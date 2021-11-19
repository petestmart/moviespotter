package com.petestmart.moviespotter.presentation.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightThemeColors = lightColors(
    primary = Gray900,
    primaryVariant = Gray700,
    onPrimary = Color.White,
    secondary = Yellow400,
    secondaryVariant = Yellow700,
    onSecondary = Color.Black,
    error = RedErrorDark,
    onError = RedErrorLight,
    background = Grey1,
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Black2,
)

private val DarkThemeColors = darkColors(
    primary = Black2,
    primaryVariant = Gray800,
    onPrimary = Color.White,
    secondary = YellowA700,
    onSecondary = Black1,
    error = RedErrorLight,
    background = Black1,
    onBackground = Color.White,
    surface = Gray900,
    onSurface = Color.White,
)

@Composable
fun AppTheme (
    darkTheme: Boolean,
    content: @Composable () -> Unit,
){
    MaterialTheme (
        colors = if(darkTheme) DarkThemeColors else LightThemeColors,
        typography = MovieSpotterTypography,
        shapes = AppShapes,
    ){
        content()
    }
}