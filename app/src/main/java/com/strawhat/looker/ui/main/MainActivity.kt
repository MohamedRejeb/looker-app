package com.strawhat.looker.ui.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.strawhat.looker.chat.SocketHandler
import com.strawhat.looker.navigation.SetupNavGraph
import com.strawhat.looker.ui.theme.LookerTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        viewModel.clearRequestLocation()

        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                // Precise location access granted.
                fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
                Toast.makeText(this, "Precise location access granted", Toast.LENGTH_SHORT).show()
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                // Only approximate location access granted.
                Toast.makeText(this, "Only approximate location access granted", Toast.LENGTH_SHORT).show()
            } else -> {
                // No location access granted.
                Toast.makeText(this, "No location access granted", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.requestLocation.collectLatest { requestLocation ->
                    if (requestLocation) {
                        val isAccessCoarseLocationPermissionGranted =
                            ContextCompat.checkSelfPermission(
                                applicationContext,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            ) == PackageManager.PERMISSION_GRANTED

                        if (!isAccessCoarseLocationPermissionGranted) {
                            locationPermissionRequest.launch(
                                arrayOf(
                                    Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.ACCESS_COARSE_LOCATION,
                                )
                            )
                        }
                    }
                }
            }
        }

        fusedLocationClient.lastLocation
            .addOnSuccessListener { it?.let { location ->
                viewModel.updateLocation(
                    LatLng(location.latitude, location.longitude)
                )
            } }

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            LookerTheme {
                val navController = rememberAnimatedNavController()
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .statusBarsPadding(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    SetupNavGraph(
                        navController = navController,
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        SocketHandler.closeConnection()
    }
}