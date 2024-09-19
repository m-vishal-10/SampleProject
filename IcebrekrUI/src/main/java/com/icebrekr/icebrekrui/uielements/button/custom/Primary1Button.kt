package com.icebrekr.icebrekrui.uielements.button.custom

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import com.icebrekr.icebrekrui.configurables.FontType
import com.icebrekr.icebrekrui.configurables.IBCustomFonts
import com.icebrekr.icebrekrui.configurables.IBFonts
import com.icebrekr.icebrekrui.configurables.color.IBTheme
import com.icebrekr.uicomponents.components.buttons.BaseButton
import com.icebrekr.uicomponents.components.buttons.properties.ButtonDimensionProperties
import com.icebrekr.uicomponents.components.buttons.properties.ButtonIconProperties
import com.icebrekr.uicomponents.components.buttons.properties.ButtonState
import com.icebrekr.uicomponents.components.buttons.properties.ButtonStyleProperties

@Composable
fun Primary1Button(
    titleText: String,
    buttonState: ButtonState,
    leftIcon: Int? = null,
    customIcon: Int? = null,
    rightIcon: Int? = null,
    action: (() -> Unit)? = null
) {
    val screenWidth =
        LocalContext.current.resources.displayMetrics.widthPixels / LocalDensity.current.density
    val dimensionProperties1 = ButtonDimensionProperties(
        width = (screenWidth*0.8f),
        height = 40f,
        cornerRadius = 26f,
        padding = 8f
    )
    val styleProperties = ButtonStyleProperties(
        font = IBFonts.appliedFont(IBCustomFonts.bold, FontType.callout).fontFamily,
        fontSize = FontType.calloutBold.fontSize,
        textForegroundColor = IBTheme.theme.content2,
        textForegroundDisabledColor = IBTheme.theme.primary2Disabled,
        defaultBackgroundColor = IBTheme.theme.primary1Default,
        pressedBackgroundColor = IBTheme.theme.primary1Pressed,
        disabledBackgroundColor = IBTheme.theme.primary1Disabled
    )
    val buttonIconProperties =
        ButtonIconProperties(leftIcon = leftIcon, customIcon = customIcon, rightIcon = rightIcon)

    BaseButton(
        titleText = titleText,
        dimensionProperties = dimensionProperties1,
        styleProperties = styleProperties,
        iconProperties = buttonIconProperties,
        buttonState = buttonState,
        action = { action?.invoke() }
    )
}

@Preview
@Composable
fun Primary1ButtonContentView() {
    Column {
        Primary1Button(
            titleText = "Default State",
            buttonState = ButtonState.DEFAULT,
            leftIcon = calendarIcon,
            customIcon = calendarIcon,
            rightIcon = calendarIcon,
            action = {

            }
        )
        Primary1Button(
            titleText = "Disabled State",
            buttonState = ButtonState.DISABLED,
            leftIcon = calendarIcon,
            customIcon = calendarIcon,
            rightIcon = calendarIcon,
            action = {

            }
        )
        Primary1Button(
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
