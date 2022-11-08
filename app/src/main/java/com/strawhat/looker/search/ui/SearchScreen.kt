package com.strawhat.looker.search.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import com.strawhat.looker.R
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import kotlin.math.absoluteValue

@Composable
fun SearchScreen(
    navigateToMap: (placeId: String) -> Unit,
    colorScheme: ColorScheme = MaterialTheme.colorScheme,
    typography: Typography = MaterialTheme.typography,
    viewModel: SearchViewModel = hiltViewModel()
) {

    LaunchedEffect(key1 = viewModel.value) {
        viewModel.search()
    }

    LaunchedEffect(key1 = viewModel.categoryId) {
        viewModel.search()
    }

    LaunchedEffect(key1 = viewModel.isAvailable) {
        viewModel.search()
    }

    SearchScreenContent(
        navigateToMap = navigateToMap,
        colorScheme = colorScheme,
        typography = typography,
        viewModel = viewModel,
        modifier = Modifier.fillMaxSize()
    )

    if (viewModel.places.isEmpty()) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            if (viewModel.isLoading) {
                CircularProgressIndicator()
            } else {
                Text(text = "There is no places...")
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Composable
fun SearchScreenContent(
    navigateToMap: (placeId: String) -> Unit,
    colorScheme: ColorScheme,
    typography: Typography,
    viewModel: SearchViewModel,
    modifier: Modifier = Modifier,
) {

    Column(
        modifier = modifier
            .padding(top = 32.dp, bottom = 20.dp)
    ) {
        var isFilterVisible by remember { mutableStateOf(true) }

        TextField(
            value = viewModel.value ?: "",
            onValueChange = { viewModel.value = it },
            shape = RoundedCornerShape(16.dp),
            placeholder = {
                Text(text = "Search...")
            },
            colors = TextFieldDefaults.textFieldColors(
                textColor = colorScheme.onSurface,
                containerColor = Color.White,
                disabledPlaceholderColor = colorScheme.outline,
                placeholderColor = colorScheme.outline,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
            ),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.search),
                    contentDescription = "Search",
                    tint = colorScheme.outline,
                    modifier = Modifier.size(24.dp),
                )
            },
            trailingIcon = {
                IconButton(onClick = { isFilterVisible = !isFilterVisible }) {
                    Icon(
                        painter = painterResource(id = R.drawable.filter),
                        contentDescription = "Search",
                        tint = colorScheme.outline,
                        modifier = Modifier.size(24.dp),
                    )
                }
            },
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth()
        )

        if (viewModel.isLoading) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .padding(horizontal = 36.dp)
                    .offset(y = (-1).dp)
            )
        }

        Spacer(modifier = Modifier.height(22.dp))

        AnimatedVisibility(visible = isFilterVisible) {

            LazyRow(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
            ) {
                items(viewModel.categories, key = { it.id }) { category ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .clickable {
                                if (viewModel.categoryId == category.id) viewModel.categoryId =
                                    null
                                else viewModel.categoryId = category.id
                            }
                            .background(
                                if (category.id == viewModel.categoryId) colorScheme.primaryContainer
                                else Color.White
                            )
                            .padding(10.dp)
                        ,
                    ) {
                        Icon(
                            painter = painterResource(id = category.iconResourceId),
                            contentDescription = category.name,
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(colorScheme.surface)
                                .padding(4.dp)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = category.name.replaceFirstChar { it.uppercase() },
                            color =
                            if (category.id == viewModel.categoryId) colorScheme.surface
                            else colorScheme.onSurface
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(22.dp))

        HorizontalPager(
            count = viewModel.places.size,
            contentPadding = PaddingValues(horizontal = 58.dp),
        ) { index ->
            val place = viewModel.places[index]

            Column(
                modifier = Modifier
                    .fillMaxSize()
//                    .padding(8.dp)
                    .graphicsLayer {
                        // Calculate the absolute offset for the current page from the
                        // scroll position. We use the absolute value which allows us to mirror
                        // any effects for both directions
                        val pageOffset = calculateCurrentOffsetForPage(index).absoluteValue

                        // We animate the scaleX + scaleY, between 85% and 100%
                        lerp(
                            start = 0.85f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        ).also { scale ->
                            scaleX = scale
                            scaleY = scale
                        }
                    }
                    .clip(RoundedCornerShape(16.dp))
                    .clickable { navigateToMap(place.id) }
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                if (place.slides.isEmpty()) {
                    Image(
                        painter = painterResource(id = R.drawable.placeholder),
                        contentDescription = "placeholder",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .clip(RoundedCornerShape(16.dp))
                    )
                } else {
                    AsyncImage(
                        model = place.slides.first().image,
                        contentDescription = "image",
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(id = R.drawable.placeholder),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .clip(RoundedCornerShape(16.dp))
                    )
                }

                Spacer(modifier = Modifier.height(22.dp))

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
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = place.name,
                    style = typography.titleLarge,
                    color = colorScheme.onSurface,
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = place.content,
                    style = typography.bodyMedium,
                    color = colorScheme.onSurface,
                )

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }


}