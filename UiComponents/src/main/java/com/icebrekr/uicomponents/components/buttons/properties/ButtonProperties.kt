package com.icebrekr.uicomponents.components.buttons.properties

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.TextUnit

data class ButtonStyleProperties(
    val font: FontFamily,
    val fontSize: TextUnit,
    val textForegroundColor: Color,
    val textForegroundPressedColor: Color? = null,
    val textForegroundDisabledColor: Color? = null,
    val defaultBackgroundColor: Color,
    val pressedBackgroundColor: Color,
    val disabledBackgroundColor: Color
)

/// A data class representing properties for customizing the dimensions of a button.
data class ButtonDimensionProperties(
    /// The width of the button.
    val width: Float,

    /// The height of the button.
    val height: Float,

    /// The corner radius of the button.
    val cornerRadius: Float,

    /// The padding of the button.
    val padding: Float
)

data class ButtonIconProperties(
    val leftIcon: Int?,
    val customIcon: Int?,
    val rightIcon: Int?
)

enum class ButtonType {
    PRIMARY1,
    PRIMARY2,
    SECONDARY,
    TERTIARY,
    TERTIARY2,
    QUATERNARY
}

enum class ButtonState {
    DEFAULT, PRESSED, DISABLED
}