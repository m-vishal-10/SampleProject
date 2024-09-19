package com.icebrekr.icebrekrui.configurables.color

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

// Enumeration defining the possible color theme modes.
enum class ThemeMode {
    Dark,
    Bright
}

// Structure representing the color theme for the IB application.
object IBTheme {
    var mode by mutableStateOf(ThemeMode.Bright)
    var theme: IBColors = IBBrightColors()

    // Function to update the color theme mode and refresh the theme accordingly.
    fun updateThemeMode(mode: ThemeMode) {
        this.mode = mode
        theme = if (mode == ThemeMode.Bright) IBBrightColors() else IBBrightColors()
    }
}
