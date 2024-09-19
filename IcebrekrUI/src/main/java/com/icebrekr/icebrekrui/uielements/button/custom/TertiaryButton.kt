package com.icebrekr.icebrekrui.uielements.button.custom

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.icebrekr.icebrekrui.R
import com.icebrekr.icebrekrui.configurables.FontType
import com.icebrekr.icebrekrui.configurables.IBCustomFonts
import com.icebrekr.icebrekrui.configurables.IBFonts
import com.icebrekr.icebrekrui.configurables.color.IBTheme
import com.icebrekr.uicomponents.components.buttons.BaseButton
import com.icebrekr.uicomponents.components.buttons.properties.ButtonDimensionProperties
import com.icebrekr.uicomponents.components.buttons.properties.ButtonIconProperties
import com.icebrekr.uicomponents.components.buttons.properties.ButtonState
import com.icebrekr.uicomponents.components.buttons.properties.ButtonStyleProperties

// Define your custom Image resource IDs here
val calendarIcon = R.drawable.ic_qr_code

@Composable
fun TertiaryButton(
    titleText: String,
    buttonState: ButtonState,
    width: Float = LocalScreenWidth() * 0.8f,
    leftIcon: Int? = null,
    customIcon: Int? = null,
    rightIcon: Int? = null,
    action: (() -> Unit)? = null,
) {

    var dimensionProperties = ButtonDimensionProperties(
        width = width,
        height = 56f,
        cornerRadius = 26f,
        padding = 8f
    )
    var styleProperties = ButtonStyleProperties(
        font = IBFonts.appliedFont(IBCustomFonts.bold,FontType.calloutBold).fontFamily,
        fontSize = FontType.calloutBold.fontSize,
        textForegroundColor = IBTheme.theme.primary1Default,
        textForegroundPressedColor = IBTheme.theme.primary1Pressed,
        textForegroundDisabledColor = IBTheme.theme.primary2Disabled,
        defaultBackgroundColor = Color.Transparent,
        pressedBackgroundColor = Color.Transparent,
        disabledBackgroundColor = Color.Transparent
    )
    var buttonIconProperties =
        ButtonIconProperties(leftIcon = leftIcon, customIcon = customIcon, rightIcon = rightIcon)

    BaseButton(
        titleText = titleText,
        dimensionProperties = dimensionProperties,
        styleProperties = styleProperties,
        iconProperties = buttonIconProperties,
        buttonState = buttonState,
        action = { action?.invoke() }
    )
}

@Preview
@Composable
fun TertiaryContentView() {
    Column {
        TertiaryButton(
            titleText = "Default State",
            buttonState = ButtonState.DEFAULT,
            leftIcon = calendarIcon,
            customIcon = calendarIcon,
            rightIcon = calendarIcon,
            action = {

            }
        )
        TertiaryButton(
            titleText = "Disabled State",
            buttonState = ButtonState.DISABLED,
            leftIcon = calendarIcon,
            customIcon = calendarIcon,
            rightIcon = calendarIcon,
            action = {

            }
        )
        TertiaryButton(
            titleText = "Pressed State",
            buttonState = ButtonState.PRESSED,
            leftIcon = calendarIcon,
            customIcon = calendarIcon,
            rightIcon = calendarIcon,
            action = {

            }
        )
    }
}
