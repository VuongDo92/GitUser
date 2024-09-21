package com.tymex.core.presentation.designsystem

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

val LightColorScheme = lightColorScheme(
    primary = TymexPurple,
    background = TymexWhite,
    surface = TymexDarkGray,
    secondary = TymexBlack,
    tertiary = TymexBlack,
    primaryContainer = TymexPurple40,
    onPrimary = TymexWhite,
    onBackground = TymexBlack,
    onSurface = TymexBlack,
    onSurfaceVariant = TymexDarkGray,
    error = TymexDarkRed
)

@Composable
fun TymexTheme(
    content: @Composable () -> Unit

) {
    val colorScheme = LightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
        }
    }
    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography,
        content = content
    )
}