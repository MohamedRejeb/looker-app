package com.strawhat.looker.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.strawhat.looker.R

sealed class SplashScreenEvent {
    object NavigateToLogin: SplashScreenEvent()
    object NavigateToHome: SplashScreenEvent()
}

@Composable
fun SplashScreen(
    navigateToLogin: () -> Unit,
    navigateToHome: () -> Unit,
    colorScheme: ColorScheme = MaterialTheme.colorScheme,
    typography: androidx.compose.material3.Typography = MaterialTheme.typography,
    viewModel: SplashViewModel = hiltViewModel(),
) {

    LaunchedEffect(key1 = viewModel.navigateToLogin) {
        if (viewModel.navigateToLogin)
            navigateToLogin()
    }

    LaunchedEffect(key1 = viewModel.navigateToHome) {
        if (viewModel.navigateToHome)
            navigateToHome()
    }

    LaunchedEffect(key1 = true) {
        viewModel.onAppear()
    }

    SplashScreenContent(
        colorScheme = colorScheme,
        typography = typography,
        modifier = Modifier.fillMaxSize(),
    )

}

@Composable
fun SplashScreenContent(
    colorScheme: ColorScheme,
    typography: androidx.compose.material3.Typography,
    modifier: Modifier = Modifier,
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        colorScheme.primary,
                        colorScheme.primaryContainer
                    )
                )
            )
    ) {
        Image(
            painter = painterResource(id = R.drawable.backround),
            contentDescription = "Background",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize(),
            alpha = 0.1f,
        )

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier,
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo3),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .shadow(12.dp, RoundedCornerShape(16.dp))
                    .background(colorScheme.surface)
                ,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Looker",
                color = colorScheme.onPrimary,
                style = typography.titleLarge
            )
        }
    }

}