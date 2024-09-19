package com.icebrekr.uicomponents.components.textfield.properties

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp

// TextFieldProperties
data class TextFieldProperties(
    var text: String = "",
    val placeholder: String,
    val title: String,
    val info: Info? = null
) {
    data class Info(
        val defaultMessage: String? = null,
        val successMessage: String? = null,
        val errorMessage: String? = null,
        val disabledMessage: String? = null,
        val warningMessage: String? = null
    )
}

// TextFieldStyleProperties
data class TextFieldStyleProperties(
    val font: FontStyle?,
    val color: ColorStyle,
    val icon: IconStyle,
    val dimension: DimensionStyle
) {
    data class FontStyle(
        val textFont: FontFamily,
        val titleFont: FontFamily,
        val characterCountDescriptionFont: FontFamily,
        val infoFont: FontFamily
    )

    data class ColorStyle(
        val textColor: Color,
        val backgroundColor: Color,
        val disabledForegroundColor: Color,
        val disabledBackgroundColor: Color,
        val titleColor: Color,
        val characterCountDescriptionColor: Color,
        val indicationColors: IndicationStyle,
        var borderColor: Color
    )

    data class IconStyle(
        val closeImage: Int,
        val secureOpenImage: Int,
        val secureCloseImage: Int,
        var primaryActionImage: Int?,
        var secondaryActionImage: Int?
    )

    data class IndicationStyle(
        val defaultColor: Color,
        val errorColor: Color,
        val warningColor: Color,
        val successColor: Color,
        val disabledColor: Color
    )

    data class DimensionStyle(
        val height: Dp,
        val cornerRadius: Dp,
        val strokeWidth: Dp
    )
}

// TextFieldState
enum class TextFieldState {
    DEFAULT, ERROR, WARNING, SUCCESS, DISABLED
}
