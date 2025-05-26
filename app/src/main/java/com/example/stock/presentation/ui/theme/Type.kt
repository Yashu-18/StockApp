package com.example.stock.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.stock.R

private val MontserratAlternates = FontFamily(
    Font(R.font.inter_light, FontWeight.Light),
    Font(R.font.inter_regular, FontWeight.Normal),
    Font(R.font.inter_medium, FontWeight.Medium),
    Font(R.font.inter_semi_bold, FontWeight.SemiBold),
    Font(R.font.inter_bold, FontWeight.Bold),
)

val Typography = Typography(
    labelSmall = TextStyle(
        fontFamily = MontserratAlternates,
        fontWeight = FontWeight.W500,
        fontSize = 11.sp,
        lineHeight = 16.sp
    ),
    labelMedium = TextStyle(
        fontFamily = MontserratAlternates,
        fontWeight = FontWeight.W500,
        fontSize = 12.sp,
        lineHeight = 16.sp
    ),
    labelLarge = TextStyle(
        fontFamily = MontserratAlternates,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = MontserratAlternates,
        fontWeight = FontWeight.W400,
        fontSize = 32.sp,
        lineHeight = 40.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = MontserratAlternates,
        fontWeight = FontWeight.W400,
        fontSize = 28.sp,
        lineHeight = 36.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = MontserratAlternates,
        fontWeight = FontWeight.W400,
        fontSize = 24.sp,
        lineHeight = 32.sp
    ),
    bodySmall = TextStyle(
        fontFamily = MontserratAlternates,
        fontWeight = FontWeight.W400,
        fontSize = 12.sp,
        lineHeight = 16.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = MontserratAlternates,
        fontWeight = FontWeight.W400,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = MontserratAlternates,
        fontWeight = FontWeight.W400,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    titleSmall = TextStyle(
        fontFamily = MontserratAlternates,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        fontWeight = FontWeight.W700
    ),
    titleMedium = TextStyle(
        fontFamily = MontserratAlternates,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        fontWeight = FontWeight.W700,
    ),
    titleLarge = TextStyle(
        fontFamily = MontserratAlternates,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        fontWeight = FontWeight.W700,
    )
)