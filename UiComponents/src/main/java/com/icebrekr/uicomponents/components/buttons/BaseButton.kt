package com.icebrekr.uicomponents.components.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.icebrekr.uicomponents.R
import com.icebrekr.uicomponents.components.buttons.properties.ButtonDimensionProperties
import com.icebrekr.uicomponents.components.buttons.properties.ButtonIconProperties
import com.icebrekr.uicomponents.components.buttons.properties.ButtonState
import com.icebrekr.uicomponents.components.buttons.properties.ButtonStyleProperties

//TODO:- Match for both platform later

@Composable
fun BaseButton(
    titleText: String,
    dimensionProperties: ButtonDimensionProperties,
    styleProperties: ButtonStyleProperties,
    iconProperties: ButtonIconProperties,
    buttonState: ButtonState = ButtonState.DEFAULT,
    action: (() -> Unit)
) {
    val backgroundColor by remember {
        derivedStateOf {
            when (buttonState) {
                ButtonState.DEFAULT -> styleProperties.defaultBackgroundColor
                ButtonState.PRESSED -> styleProperties.pressedBackgroundColor
                ButtonState.DISABLED -> styleProperties.disabledBackgroundColor
            }
        }
    }
    val textColor by remember {
        derivedStateOf {
            when (buttonState) {
                ButtonState.DEFAULT -> styleProperties.textForegroundColor
                ButtonState.PRESSED -> styleProperties.textForegroundPressedColor
                    ?: styleProperties.textForegroundColor

                ButtonState.DISABLED -> styleProperties.textForegroundDisabledColor
                    ?: styleProperties.textForegroundColor
            }
        }
    }

    Button(
        onClick = { action.invoke() },
        shape = RoundedCornerShape(dimensionProperties.cornerRadius.dp),
        border = BorderStroke(dimensionProperties.cornerRadius.dp, Color.Transparent),
        elevation = null,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor
        ),
        modifier = Modifier
            .width(dimensionProperties.width.dp)
            .height(dimensionProperties.height.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            iconProperties.leftIcon?.let {
                ImageButton(
                    image = it,
                    foregroundColor = textColor,
                    dimension = Pair(20f, 20f),
                    onTap = {
                        action.invoke()
                    }
                )
            }
            Text(
                titleText, color = textColor,
                fontFamily = styleProperties.font,
                modifier = Modifier.padding(start = 8.dp, end = 8.dp)
            )
            iconProperties.rightIcon?.let {
                ImageButton(
                    image = it,
                    foregroundColor = textColor,
                    dimension = Pair(20f, 20f),
                    onTap = {
                        action.invoke()
                    }
                )
            }
        }
    }
}

// Sample usage in a Composable function
@Preview(showBackground = true)
@Composable
fun BaseButtonExample() {

    BaseButton(
        titleText = "Press Me",
        dimensionProperties = ButtonDimensionProperties(
            width = (200f),
            height = 56f,
            cornerRadius = 200f,
            padding = 8f
        ),
        styleProperties = ButtonStyleProperties(
            font = FontFamily.Default,
            fontSize = 16.sp,
            textForegroundColor = Color.White,
            defaultBackgroundColor = Color.Blue,
            pressedBackgroundColor = Color.Red,
            disabledBackgroundColor = Color.Gray
        ),
        iconProperties = ButtonIconProperties(leftIcon = R.drawable.ic_back_arrow, null, null),
        buttonState = ButtonState.DISABLED,
        action = { /* Handle button click */ }
    )
}
