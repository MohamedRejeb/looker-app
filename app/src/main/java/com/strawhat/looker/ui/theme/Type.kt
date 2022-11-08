package com.strawhat.looker.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.strawhat.looker.R

val ProximaNova = FontFamily(
    Font(R.font.proxima_nova_thin, weight = FontWeight.Thin),
    Font(R.font.proxima_nova_light, weight = FontWeight.Light),
    Font(R.font.proxima_nova_regular, weight = FontWeight.Normal),
    Font(R.font.proxima_nova_semibold, weight = FontWeight.SemiBold),
    Font(R.font.proxima_nova_bold, weight = FontWeight.Bold),
    Font(R.font.proxima_nova_extrabold, weight = FontWeight.ExtraBold),
    Font(R.font.proxima_nova_black, weight = FontWeight.Black),
)

// Set of Material typography styles to start with
val Typography = Typography(

    displayLarge = TextStyle(
        fontFamily = ProximaNova,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 44.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp
    ),
    displayMedium = TextStyle(
        fontFamily = ProximaNova,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
        lineHeight = 12.sp,
        letterSpacing = 0.sp
    ),
    displaySmall = TextStyle(
        fontFamily = ProximaNova,
        fontWeight = FontWeight.SemiBold,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp
    ),

    headlineLarge = TextStyle(
        fontFamily = ProximaNova,
        fontWeight = FontWeight.Black,
        fontSize = 36.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = ProximaNova,
        fontWeight = FontWeight.Black,
        fontSize = 36.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = ProximaNova,
        fontWeight = FontWeight.Black,
        fontSize = 36.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp
    ),

    titleLarge = TextStyle(
        fontFamily = ProximaNova,
        fontWeight = FontWeight.Bold,
        fontSize = 26.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp
    ),
    titleMedium = TextStyle(
        fontFamily = ProximaNova,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    ),
    titleSmall = TextStyle(
        fontFamily = ProximaNova,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),

    bodyLarge = TextStyle(
        fontFamily = ProximaNova,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = ProximaNova,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodySmall = TextStyle(
        fontFamily = ProximaNova,
        fontWeight = FontWeight.Light,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),

    labelLarge = TextStyle(
        fontFamily = ProximaNova,
        fontWeight = FontWeight.Bold,
        fontSize = 15.sp,
        lineHeight = 0.sp,
        letterSpacing = 1.sp
    ),
    labelMedium = TextStyle(
        fontFamily = ProximaNova,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.6.sp
    ),
    labelSmall = TextStyle(
        fontFamily = ProximaNova,
        fontWeight = FontWeight.SemiBold,
        fontSize = 13.sp,
        lineHeight = 17.sp,
        letterSpacing = 0.5.sp
    )

)