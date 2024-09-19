package com.icebrekr.icebrekrui.configurables.color

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class IBCustomColors(
    override var primary1Default: Color,
    override var primary1Hover: Color,
    override var primary1Pressed: Color,
    override var primary1Disabled: Color,
    override var primary2Default: Color,
    override var primary2Hover: Color,
    override var primary2Pressed: Color,
    override var primary2Disabled: Color,
    override var secondaryDefault: Color,
    override var secondaryHover: Color,
    override var secondaryPressed: Color,
    override var content1: Color,
    override var content2: Color,
    override var black: Color,
    override var white: Color,
    override var grey1: Color,
    override var grey2: Color,
    override var grey3: Color,
    override var grey4: Color,
    override var grey5: Color,
    override var grey6: Color,
    override var danger: Color,
    override var warning: Color,
    override var success: Color
) : IBColors

@Composable
fun IBCustomColorsPreview(customColors: IBColors) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            ColorItemView(color = customColors.primary1Default, name = "Primary 1 Default")
            ColorItemView(color = customColors.primary1Hover, name = "Primary 1 Hover")
            ColorItemView(color = customColors.primary1Pressed, name = "Primary 1 Pressed")
            ColorItemView(color = customColors.primary1Disabled, name = "Primary 1 Disabled")
            Spacer(modifier = Modifier.height(16.dp))
            ColorItemView(color = customColors.primary2Default, name = "Primary 2 Default")
            ColorItemView(color = customColors.primary2Hover, name = "Primary 2 Hover")
            ColorItemView(color = customColors.primary2Pressed, name = "Primary 2 Pressed")
            ColorItemView(color = customColors.primary2Disabled, name = "Primary 2 Disabled")
            Spacer(modifier = Modifier.height(16.dp))
            ColorItemView(color = customColors.secondaryDefault, name = "Secondary Default")
            ColorItemView(color = customColors.secondaryHover, name = "Secondary Hover")
            ColorItemView(color = customColors.secondaryPressed, name = "Secondary Pressed")
            Spacer(modifier = Modifier.height(16.dp))
            ColorItemView(color = customColors.content1, name = "Content 1")
            ColorItemView(color = customColors.content2, name = "Content 2")
            Spacer(modifier = Modifier.height(16.dp))
            ColorItemView(color = customColors.black, name = "Black")
            ColorItemView(color = customColors.white, name = "White")
            Spacer(modifier = Modifier.height(16.dp))
            ColorItemView(color = customColors.grey1, name = "Grey 1")
            ColorItemView(color = customColors.grey2, name = "Grey 2")
            ColorItemView(color = customColors.grey3, name = "Grey 3")
            ColorItemView(color = customColors.grey4, name = "Grey 4")
            ColorItemView(color = customColors.grey5, name = "Grey 5")
            ColorItemView(color = customColors.grey6, name = "Grey 6")
            Spacer(modifier = Modifier.height(16.dp))
            ColorItemView(color = customColors.danger, name = "Danger")
            ColorItemView(color = customColors.warning, name = "Warning")
            ColorItemView(color = customColors.success, name = "Success")
        }
    }
}

@Preview
@Composable
fun IBCustomColorsPreviewPreview() {
    val customColors = IBCustomColors(
        primary1Default = Color.Red,
        primary1Hover = Color.Magenta,
        primary1Pressed = Color.Cyan,
        primary1Disabled = Color.Gray,
        primary2Default = Color.Blue,
        primary2Hover = Color.Green,
        primary2Pressed = Color.Yellow,
        primary2Disabled = Color.Gray,
        secondaryDefault = Color.Magenta,
        secondaryHover = Color.Blue,
        secondaryPressed = Color.Blue,
        content1 = Color.Black,
        content2 = Color.White,
        black = Color.Black,
        white = Color.White,
        grey1 = Color.Gray,
        grey2 = Color.Gray,
        grey3 = Color.Gray,
        grey4 = Color.Gray,
        grey5 = Color.Gray,
        grey6 = Color.Gray,
        danger = Color.Red,
        warning = Color.Yellow,
        success = Color.Green
    )

    IBCustomColorsPreview(customColors = customColors)
}
