package com.jhoangamarra.emovie.lib.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

private val DarkColorPalette = darkColors(
    primary = Neutral1,
    primaryVariant = Neutral2,
    secondary = Shadow1
)

private val LightColorPalette = lightColors(
    primary = Neutral4,
    primaryVariant = Neutral1,
    secondary = Shadow1,
)

@Composable
fun EMovieTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        DarkColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

@Composable
private fun isSmallScreen() = LocalConfiguration.current.screenWidthDp > 360
