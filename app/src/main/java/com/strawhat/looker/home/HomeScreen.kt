package com.strawhat.looker.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HomeScreen(
    colorScheme: ColorScheme = MaterialTheme.colorScheme,
    typography: Typography = MaterialTheme.typography,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    HomeScreenContent(
        colorScheme = colorScheme,
        typography = typography,
        modifier = Modifier.fillMaxSize(),
    )
}

@Composable
fun HomeScreenContent(
    colorScheme: ColorScheme,
    typography: Typography,
    modifier: Modifier = Modifier,
) {

    

}