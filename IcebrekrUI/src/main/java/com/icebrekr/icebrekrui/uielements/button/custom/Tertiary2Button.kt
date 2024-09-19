package com.icebrekr.icebrekrui.uielements.button.custom

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
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

@Composable
fun Tertiary2Button(
    titleText: String,
    width: Float = LocalScreenWidth() * 0.8f,
    buttonState: ButtonState,
    leftIcon: Int? = null,
    customIcon: Int? = null,
    rightIcon: Int? = null,
    action: () -> Unit
) {
    val dimensionProperties = ButtonDimensionProperties(
        width = width,
        height = 56f,
        cornerRadius = 26f,
        padding = 8f
    )

    var styleProperties = ButtonStyleProperties(
        font = IBFonts.appliedFont(IBCustomFonts.bold, FontType.calloutBold).fontFamily,
        fontSize = FontType.calloutBold.fontSize,
        textForegroundColor = IBTheme.theme.primary2Default,
        textForegroundPressedColor = IBTheme.theme.primary2Pressed,
        textForegroundDisabledColor = IBTheme.theme.primary2Disabled,
        defaultBackgroundColor = Color.Transparent,
        pressedBackgroundColor = Color.Transparent,
        disabledBackgroundColor = Color.Transparent
    )

    val iconProperties = ButtonIconProperties(
        leftIcon = leftIcon,
        customIcon = customIcon,
        rightIcon = rightIcon
    )

    BaseButton(
        titleText = titleText,
        dimensionProperties = dimensionProperties,
        styleProperties = styleProperties,
        iconProperties = iconProperties,
        buttonState = buttonState,
        action = action
    )
}

@Preview
@Composable
fun Tertiary2ContentView() {
    Column {
        var buttonState by remember { mutableStateOf(ButtonState.DEFAULT) }
        Tertiary2Button(
            titleText = "Default State",
            buttonState = ButtonState.DEFAULT,
            leftIcon = R.drawable.ic_back_arrow,
            customIcon = R.drawable.ic_back_arrow,
            rightIcon = R.drawable.ic_back_arrow
        ) {
            // Handle button click action
        }

        Tertiary2Button(
            titleText = "Disabled State",
            buttonState = ButtonState.DISABLED,
            leftIcon = R.drawable.ic_back_arrow,
            customIcon = R.drawable.ic_back_arrow,
            rightIcon = R.drawable.ic_back_arrow
        ) {
            // Handle button click action
        }

        Tertiary2Button(
            titleText = "Pressed State",
            buttonState = ButtonState.PRESSED,
            leftIcon = R.drawable.ic_back_arrow,
            customIcon = R.drawable.ic_back_arrow,
            rightIcon = R.drawable.ic_back_arrow
        ) {
            // Handle button click action
        }
    }
}

@Composable
fun LocalScreenWidth(): Float {
    val screenWidth =
        LocalContext.current.resources.displayMetrics.widthPixels / LocalDensity.current.density
    return remember(screenWidth) { screenWidth }
}
