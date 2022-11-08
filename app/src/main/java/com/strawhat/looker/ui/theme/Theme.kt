package com.strawhat.looker.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorScheme = darkColorScheme(
    primary = Green500,
    primaryContainer = Green900,
    onPrimary = White1000,
    secondary = Yellow500,
    onSecondary = Black800,
    tertiary = Red500,
    onTertiary = White1000,

    background = Gray400,
    onBackground = Black800,
    surface = Gray400,
    onSurfaceVariant = Black700,
    onSurface = Black800,
    outline = Gray500,
    error = Red500,
    onError = White1000,
)

private val LightColorScheme = lightColorScheme(
    primary = Green500,
    primaryContainer = Green900,
    inversePrimary = Green200,
    onPrimary = White1000,
    secondary = Yellow500,
    onSecondary = Black800,
    tertiary = Red500,
    onTertiary = White1000,

    background = Gray400,
    onBackground = Black800,
    surface = Gray400,
    onSurfaceVariant = Black700,
    onSurface = Black800,
    outline = Gray500,
    error = Red500,
    onError = White1000,
)

@Composable
fun LookerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            (view.context as Activity).window.statusBarColor = colorScheme.primary.toArgb()
            ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars = darkTheme
        }
    }

    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(color = Color.Transparent)

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}