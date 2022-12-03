package com.strawhat.looker.map.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Typography
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.strawhat.looker.R
import com.strawhat.looker.map.domain.model.Place
import com.strawhat.looker.map.utils.animationToPosition
import com.strawhat.looker.map.utils.getBitmapDescriptorFromVector
import com.strawhat.looker.review.domain.model.Review
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.math.roundToInt

const val myLat = 35.8333340216031
const val myLng = 10.600116178393362

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MapScreen(
    context: Context = LocalContext.current,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    viewModel: MapViewModel = hiltViewModel(),
) {
    val colorScheme = androidx.compose.material3.MaterialTheme.colorScheme
    val typography = androidx.compose.material3.MaterialTheme.typography

    val location by viewModel.location.collectAsState()

    viewModel.isLocationPermissionDenied = location.latitude == 0.0 && location.longitude == 0.0

    val mapUiSettings = remember {
        MapUiSettings(
            zoomControlsEnabled = true,
            myLocationButtonEnabled = true,
        )
    }

    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )

    val cameraPositionState: CameraPositionState = rememberCameraPositionState {
        this.position = CameraPosition
            .Builder()
            .target(LatLng(myLat, myLng))
            .zoom(14f)
            .build()
    }

//    cameraPositionState.animationToPosition(LatLng(myLat, myLng), coroutineScope)

    LaunchedEffect(key1 = viewModel.selectedPlaceId) {
        Timber.d("selectedPlaceId: ${viewModel.selectedPlaceId}")
        viewModel.places.find { it.id == viewModel.selectedPlaceId }?.let { place ->
            viewModel.getReviews()
            cameraPositionState.animationToPosition(LatLng(place.lat, place.long), coroutineScope)
            coroutineScope.launch {
                modalBottomSheetState.animateTo(ModalBottomSheetValue.Expanded)
            }
        }
    }

    LaunchedEffect(key1 = modalBottomSheetState.isVisible) {
        if (!modalBottomSheetState.isVisible) {
            viewModel.selectedPlaceId = null
            viewModel.selectedProductId = null
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background)
    ) {
        LaunchedEffect(key1 = location) {
            if (location.longitude != 0.0) {
                cameraPositionState.animationToPosition(location, coroutineScope)
            }
        }

        GoogleMap(
            modifier = Modifier
                .fillMaxSize()
            ,
            properties = viewModel.state.properties,
            uiSettings = mapUiSettings,
            cameraPositionState = cameraPositionState,
        ) {
            Marker(
                position = if (location.longitude != 0.0) location else LatLng(myLat, myLng),
                title = "Your current location",
                snippet = "This is your location",
                onInfoWindowLongClick = {

                },
                onClick = {
//                    cameraPositionState.animationToPosition(location, coroutineScope)
                    false
                },
                icon = getBitmapDescriptorFromVector(
                    context = context,
                    id = R.drawable.location_fill,
                    width = 24,
                    height = 24,
                ),
            )

            viewModel.places.forEach { place ->
                Marker(
                    position = LatLng(place.lat, place.long),
                    title = "Your current location",
                    snippet = "Demo Description...",
                    onInfoWindowLongClick = {

                    },
                    onClick = {
                        viewModel.selectedPlaceId = place.id
                        true
                    },
                    icon = getBitmapDescriptorFromVector(
                        context = context,
                        id = place.category.iconResourceId,
                        width = 24,
                        height = 24,
                    ),
                )
            }
        }
    }

    ModalBottomSheetLayout(
        sheetBackgroundColor = colorScheme.surface,
        sheetState = modalBottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                viewModel.places.find { it.id == viewModel.selectedPlaceId }?.let { selectedPlace ->

                    BottomSheetContent(
                        colorScheme = colorScheme,
                        typography = typography,
                        place = selectedPlace,
                        viewModel = viewModel,
                    )

                    LaunchedEffect(key1 = viewModel.isUpdateProductStateError) {
                        if (viewModel.isUpdateProductStateError) {
                            Toast.makeText(
                                context,
                                "Something went wrong, please try again later",
                                Toast.LENGTH_SHORT
                            ).show()
                            viewModel.isUpdateProductStateError = false
                        }
                    }

                    LaunchedEffect(key1 = viewModel.isUpdateProductStateSuccess) {
                        if (viewModel.isUpdateProductStateSuccess) {
                            viewModel.selectedProductId = null
                            Toast.makeText(
                                context,
                                "Product status updated successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                            viewModel.isUpdateProductStateSuccess = false
                        }
                    }

                    selectedPlace.category.products
                        .find { it.id == viewModel.selectedProductId }
                        ?.let { product ->

                        Dialog(
                            onDismissRequest = { viewModel.selectedProductId = null },
                            properties = DialogProperties(
                                dismissOnBackPress = true,
                                dismissOnClickOutside = true,
                            ),
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(16.dp))
                                ,
                            ) {
                                Column(
                                    modifier = Modifier
                                        .background(colorScheme.surface)
                                        .padding(20.dp)
                                ) {
                                    Text(
                                        text = "Update product status:",
                                        color = colorScheme.onSurface,
                                        style = typography.titleMedium,
                                    )

                                    Spacer(modifier = Modifier.height(10.dp))

                                    TextButton(
                                        onClick = {
                                            viewModel.updateProductState(isAvailable = true)
                                        },
                                        enabled = !viewModel.isUpdateProductStateLoading,
                                    ) {
                                        Text(
                                            text = "Available",
                                            color = colorScheme.primary,
                                            style = typography.labelLarge,
                                        )

                                        Spacer(modifier = Modifier.weight(1f))

                                        RadioButton(
                                            selected = product.status == Place.Category.Product.Status.Available,
                                            onClick = { viewModel.updateProductState(isAvailable = true) },
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(10.dp))

                                    TextButton(
                                        onClick = {
                                            viewModel.updateProductState(isAvailable = false)
                                        },
                                        enabled = !viewModel.isUpdateProductStateLoading,
                                    ) {
                                        Text(
                                            text = "Not available",
                                            color = colorScheme.error,
                                            style = typography.labelLarge,
                                        )

                                        Spacer(modifier = Modifier.weight(1f))

//                        Box(
//                            modifier = Modifier
//                                .size(20.dp)
//                                .border(2.dp, colorScheme.primary, CircleShape)
//                                .padding(5.dp)
//                                .clip(CircleShape)
//                                .background(colorScheme.primary)
//                        )
                                        RadioButton(
                                            selected = product.status == Place.Category.Product.Status.Unavailable,
                                            onClick = { viewModel.updateProductState(isAvailable = false) },
                                        )
                                    }
                                }

                                if (viewModel.isUpdateProductStateLoading) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .alpha(0.5f)
                                            .background(colorScheme.onSurface)
                                        ,
                                    )

                                    Column(
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                    ) {
                                        CircularProgressIndicator(
                                            color = colorScheme.onSurface,
                                        )

                                        Spacer(modifier = Modifier.height(14.dp))

                                        Text(
                                            text = "Updating product status...",
                                            color = colorScheme.surface,
                                            style = typography.labelMedium,
                                        )
                                    }
                                }
                            }

                        }
                    }

                    selectedPlace.category.products
                        .find { it.id == viewModel.selectedReviewId }
                        ?.let { product ->

                            Dialog(
                                onDismissRequest = { viewModel.selectedReviewId = null },
                                properties = DialogProperties(
                                    dismissOnBackPress = true,
                                    dismissOnClickOutside = true,
                                ),
                            ) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(16.dp))
                                    ,
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .background(colorScheme.surface)
                                            .padding(20.dp)
                                    ) {
                                        Text(
                                            text = "Update product status:",
                                            color = colorScheme.onSurface,
                                            style = typography.titleMedium,
                                        )

                                        Spacer(modifier = Modifier.height(10.dp))

                                        TextButton(
                                            onClick = {
                                                viewModel.updateProductState(isAvailable = true)
                                            },
                                            enabled = !viewModel.isUpdateProductStateLoading,
                                        ) {
                                            Text(
                                                text = "Available",
                                                color = colorScheme.primary,
                                                style = typography.labelLarge,
                                            )

                                            Spacer(modifier = Modifier.weight(1f))

                                            RadioButton(
                                                selected = product.status == Place.Category.Product.Status.Available,
                                                onClick = { viewModel.updateProductState(isAvailable = true) },
                                            )
                                        }

                                        Spacer(modifier = Modifier.height(10.dp))

                                        TextButton(
                                            onClick = {
                                                viewModel.updateProductState(isAvailable = false)
                                            },
                                            enabled = !viewModel.isUpdateProductStateLoading,
                                        ) {
                                            Text(
                                                text = "Not available",
                                                color = colorScheme.error,
                                                style = typography.labelLarge,
                                            )

                                            Spacer(modifier = Modifier.weight(1f))

//                        Box(
//                            modifier = Modifier
//                                .size(20.dp)
//                                .border(2.dp, colorScheme.primary, CircleShape)
//                                .padding(5.dp)
//                                .clip(CircleShape)
//                                .background(colorScheme.primary)
//                        )
                                            RadioButton(
                                                selected = product.status == Place.Category.Product.Status.Unavailable,
                                                onClick = { viewModel.updateProductState(isAvailable = false) },
                                            )
                                        }
                                    }

                                    if (viewModel.isUpdateProductStateLoading) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .alpha(0.5f)
                                                .background(colorScheme.onSurface)
                                            ,
                                        )

                                        Column(
                                            verticalArrangement = Arrangement.Center,
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                        ) {
                                            CircularProgressIndicator(
                                                color = colorScheme.onSurface,
                                            )

                                            Spacer(modifier = Modifier.height(14.dp))

                                            Text(
                                                text = "Updating product status...",
                                                color = colorScheme.surface,
                                                style = typography.labelMedium,
                                            )
                                        }
                                    }
                                }

                            }
                        }
                }
            }
        }
    ) {
    }

}

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetContent(
    colorScheme: ColorScheme,
    typography: Typography,
    place: Place,
    viewModel: MapViewModel,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
) {

    Column(
        modifier = Modifier
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Box(modifier = Modifier
                .height(4.dp)
                .width(36.dp)
                .offset(y = (-24).dp)
                .clip(RoundedCornerShape(10.dp))
                .background(colorScheme.primaryContainer)
            )
        }

        val pagerState = rememberPagerState()

        if (place.slides.isNotEmpty()) {
            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier,
            ) {
                HorizontalPager(
                    count = place.slides.size,
                    key = { place.slides[it].id },
                    state = pagerState,
                    itemSpacing = 10.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                ) { page ->
                    AsyncImage(
                        model = place.slides[page].image,
                        contentDescription = "Slide $page",
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(id = R.drawable.placeholder),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(16.dp))
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    colorScheme.primaryContainer
                                )
                            )
                        )
                )

                Row {
                    place.slides.forEachIndexed { index, _ ->
                        FilledTonalButton(
                            onClick = {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            },
                            colors = ButtonDefaults.filledTonalButtonColors(
                                containerColor = colorScheme.surface
                            ),
                            modifier = Modifier
                                .padding(vertical = 10.dp, horizontal = 6.dp)
                                .height(4.dp)
                                .width(
                                    if (pagerState.currentPage == index) 36.dp
                                    else 10.dp
                                )
                        ) {}
                    }
                }
            }
        }
        else {
            Image(
                painter = painterResource(id = R.drawable.placeholder),
                contentDescription = "placeholder",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = place.name,
            style = typography.titleLarge,
            color = colorScheme.onSurface,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(id = place.category.iconResourceId),
                contentDescription = "category",
                tint = colorScheme.inversePrimary,
                modifier = Modifier.size(24.dp),
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = place.category.name.replaceFirstChar { it.uppercase() },
                style = typography.bodyMedium,
                color = colorScheme.onSurface,
            )

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                Icons.Rounded.Star,
                contentDescription = "start",
                tint = colorScheme.secondary,
                modifier = Modifier.size(24.dp),
            )

            Spacer(modifier = Modifier.width(8.dp))

            if (!viewModel.isGetReviewsLoading) {
                Text(
                    text = "${viewModel.reviewsAverage} (${viewModel.reviewsCount} review${
                        if (viewModel.reviewsCount > 1) "s" else ""
                    })",
                    style = typography.bodyMedium,
                    color = colorScheme.onSurface,
                )
            } else {
                val infiniteTransition = rememberInfiniteTransition()
                val alpha by infiniteTransition.animateFloat(
                    initialValue = .4f,
                    targetValue = 1f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(400, easing = LinearEasing),
                        repeatMode = RepeatMode.Reverse,
                    )
                )

                Box(
                    modifier = Modifier
                        .height(22.dp)
                        .width(72.dp)
                        .alpha(alpha)
                        .clip(RoundedCornerShape(16f))
                        .background(colorScheme.outline)
                )
            }


        }

        if (place.content.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = place.content,
                style = typography.bodyMedium,
                color = colorScheme.onSurface,
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Divider(color = colorScheme.outline)

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Products:",
            style = typography.titleLarge,
            color = colorScheme.onSurface,
        )

        if (place.category.products.isEmpty()) {
            Spacer(modifier = Modifier.height(46.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = "There is no products",
                    style = typography.bodyLarge,
                    color = colorScheme.onSurface,
                )
            }

            Spacer(modifier = Modifier.height(46.dp))
        } else {
            Spacer(modifier = Modifier.height(16.dp))

            place.category.products.forEach { product ->
                ProductContent(
                    colorScheme = colorScheme,
                    typography = typography,
                    product = product,
                    onProductStateEditEvent = { viewModel.selectedProductId = product.id },
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        Spacer(modifier = Modifier.height(12.dp))

        Divider(color = colorScheme.outline)

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Reviews:",
            style = typography.titleLarge,
            color = colorScheme.onSurface,
        )

        if (
            viewModel.reviews.firstOrNull()?.isCurrentUser != true && !viewModel.isGetReviewsLoading
            || viewModel.isEditReviewActive
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp, bottom = 12.dp)
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    repeat(5) { index ->
                        IconButton(onClick = { viewModel.addReviewRate = index + 1 }) {
                            Icon(
                                Icons.Rounded.Star,
                                contentDescription = "options",
                                tint =
                                if (index + 1 > viewModel.addReviewRate) colorScheme.outline
                                else colorScheme.secondary
                                ,
                                modifier = Modifier.size(36.dp),
                            )
                        }
                    }
                    Spacer(modifier = Modifier.weight(1f))
                }

                OutlinedTextField(
                    value = viewModel.addReviewContent,
                    onValueChange = { viewModel.addReviewContent = it },
                    placeholder = {
                        Text(text = "Share details of your experience at this place")
                    },
                    maxLines = 5,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                )

                Row {
                    OutlinedButton(
                        onClick = {
                            if (viewModel.isEditReviewActive) viewModel.updateReview()
                            else viewModel.addReview()
                        }
                    ) {
                        if (
                            viewModel.isAddReviewLoading && !viewModel.isEditReviewActive
                            || viewModel.isEditReviewLoading && viewModel.isEditReviewActive
                        ) {
                            CircularProgressIndicator(
                                strokeWidth = 1.dp,
                                modifier = Modifier.size(24.dp),
                            )

                            Spacer(modifier = Modifier.width(16.dp))
                        }
                        Text(
                            text = if (viewModel.isEditReviewActive) "Edit review" else "Add review",
                            style = typography.bodyLarge,
                            modifier = Modifier.padding(vertical = 8.dp),
                        )
                    }

                    if (viewModel.isEditReviewActive) {
                        Spacer(modifier = Modifier.width(16.dp))

                        OutlinedButton(
                            onClick = {
                                viewModel.addReviewContent = ""
                                viewModel.addReviewRate = 0
                                viewModel.isEditReviewActive = false
                            },
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = colorScheme.error,
                            ),
                            border = BorderStroke(
                                width = ButtonDefaults.outlinedButtonBorder.width,
                                color = colorScheme.error,
                            )
                        ) {
                            Text(
                                text = "Cancel",
                                style = typography.bodyLarge,
                                modifier = Modifier.padding(vertical = 8.dp),
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Divider(color = colorScheme.outline)
            }
        }

        if (viewModel.reviews.isEmpty()) {
            Spacer(modifier = Modifier.height(46.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (viewModel.isGetReviewsLoading) {
                    CircularProgressIndicator()
                } else {
                    Text(
                        text = "There is no reviews",
                        style = typography.bodyLarge,
                        color = colorScheme.onSurface,
                    )
                }
            }

            Spacer(modifier = Modifier.height(46.dp))
        }

        viewModel.reviews.forEachIndexed { index, review ->
            if (!review.isCurrentUser || !viewModel.isEditReviewActive) {
                ReviewContent(
                    colorScheme = colorScheme,
                    typography = typography,
                    review = review,
                    onEditEvent = {
                        viewModel.addReviewContent = review.content
                        viewModel.addReviewRate = review.rate.roundToInt()
                        viewModel.isEditReviewActive = true
                    },
                    onDeleteEvent = { viewModel.deleteReview() },
                    isDeleteReviewLoading = viewModel.isDeleteReviewLoading,
                )

                if (index < viewModel.reviews.lastIndex) {
                    Divider(color = colorScheme.outline)
                }
            }

        }
    }
}

@Composable
fun ProductContent(
    colorScheme: ColorScheme,
    typography: Typography,
    product: Place.Category.Product,
    onProductStateEditEvent: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        modifier = modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth()
        ,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .padding(horizontal = 10.dp, vertical = 16.dp)

        ) {
            AsyncImage(
                model = product.image,
                contentDescription = product.name,
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.placeholder),
                error = painterResource(id = R.drawable.placeholder),
                modifier = Modifier
                    .size(24.dp)
                    .clip(RoundedCornerShape(16.dp))
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = product.name.replaceFirstChar { it.uppercase() },
                style = typography.bodyMedium,
                color = colorScheme.onSurface,
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = product.statusText,
                style = typography.labelLarge,
                color = when(product.status) {
                    Place.Category.Product.Status.Available -> colorScheme.primary
                    Place.Category.Product.Status.Unavailable -> colorScheme.error
                    Place.Category.Product.Status.Unknown -> colorScheme.outline
                },
            )

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(onClick = {
                onProductStateEditEvent()
            }) {
                Icon(Icons.Rounded.Edit, contentDescription = "Edit")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewContent(
    colorScheme: ColorScheme,
    typography: Typography,
    review: Review,
    onEditEvent: () -> Unit,
    onDeleteEvent: () -> Unit,
    isDeleteReviewLoading: Boolean,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = modifier
                .alpha(
                    if (isDeleteReviewLoading && review.isCurrentUser) 0.4f else 1f
                )
                .fillMaxWidth()
                .padding(vertical = 24.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
            ) {

                if (review.userImage.isEmpty()) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(colorScheme.primaryContainer)
                        ,
                    ) {
                        Text(
                            text = review.userName.firstOrNull()?.toString() ?: "",
                            color = colorScheme.onPrimary,
                            style = typography.bodyMedium,
                        )
                    }
                } else {
                    AsyncImage(
                        model = review.userImage,
                        contentDescription = "avatar",
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(id = R.drawable.placeholder),
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = review.userName,
                    color = colorScheme.onSurface,
                    style = typography.bodyMedium,
                )

                Spacer(modifier = Modifier.weight(1f))

                if (review.isCurrentUser) {
                    var expanded by remember { mutableStateOf(false) }

                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = {
                            if (!isDeleteReviewLoading) {
                                expanded = !expanded
                            }
                        },
                    ) {
                        IconButton(
                            onClick = { },
                            modifier = Modifier.menuAnchor()
                        ) {
                            Icon(
                                Icons.Rounded.MoreVert,
                                contentDescription = "options",
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                        ) {
                            DropdownMenuItem(
                                text = {
                                    Icon(
                                        Icons.Rounded.Edit,
                                        contentDescription = "Edit"
                                    )
                                },
                                onClick = {
                                    if (!isDeleteReviewLoading) {
                                        onEditEvent()
                                    }
                                    expanded = false
                                },
                            )

                            DropdownMenuItem(
                                text = {
                                    Icon(
                                        Icons.Rounded.Delete,
                                        contentDescription = "Delete"
                                    )
                                },
                                onClick = {
                                    if (!isDeleteReviewLoading) {
                                        onDeleteEvent()
                                    }
                                    expanded = false
                                },
                            )
                        }
                    }
                }

            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                repeat(5) { index ->
                    Icon(
                        Icons.Rounded.Star,
                        contentDescription = "options",
                        tint =
                            if (index + 1 > review.rate) colorScheme.outline
                            else colorScheme.secondary
                        ,
                        modifier = Modifier.size(24.dp),
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = review.createdAt.substring(0..9),
                    color = colorScheme.onSurfaceVariant,
                    style = typography.labelMedium
                )
            }

            if (review.content.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = review.content,
                    color = colorScheme.onSurface,
                    style = typography.bodyMedium
                )
            }

        }

        if (isDeleteReviewLoading && review.isCurrentUser) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                CircularProgressIndicator()
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = "Deleting review...")
            }
        }
    }
}