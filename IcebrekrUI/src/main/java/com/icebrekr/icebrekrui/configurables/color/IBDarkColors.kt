package com.icebrekr.icebrekrui.configurables.color

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class IBDarkColors : IBColors {

    override val primary1Default: Color = Color(0.36f, 0.52f, 0.84f)
    override val primary1Hover: Color = Color(0.52f, 0.64f, 0.88f)
    override val primary1Pressed: Color = Color(0.2f, 0.4f, 0.8f)
    override val primary1Disabled: Color = Color(0.19f, 0.19f, 0.19f)

    override val primary2Default: Color = Color(1f, 0.2f, 0.4f)
    override val primary2Hover: Color = Color(1f, 0.4f, 0.55f)
    override val primary2Pressed: Color = Color(1f, 0f, 0.25f)
    override val primary2Disabled: Color = Color(0.4f, 0.4f, 0.4f)

    override val secondaryDefault: Color = Color(0.57f, 0.55f, 0.65f)
    override val secondaryHover: Color = Color(0.68f, 0.66f, 0.74f)
    override val secondaryPressed: Color = Color(0.47f, 0.44f, 0.56f)

    override val content1: Color = Color(1f, 1f, 1f)
    override val content2: Color = Color(0f, 0f, 0f)

    override val black: Color = Color(0f, 0f, 0f)
    override val white: Color = Color(1f, 1f, 1f)

    override val grey1: Color = Color(0.56f, 0.56f, 0.58f)
    override val grey2: Color = Color(0.39f, 0.39f, 0.4f)
    override val grey3: Color = Color(0.28f, 0.28f, 0.29f)
    override val grey4: Color = Color(0.23f, 0.23f, 0.24f)
    override val grey5: Color = Color(0.17f, 0.17f, 0.18f)
    override val grey6: Color = Color(0.11f, 0.11f, 0.12f)

    override val danger: Color = Color(1f, 0.27f, 0.23f)
    override val warning: Color = Color(1f, 0.62f, 0.04f)
    override val success: Color = Color(0.19f, 0.82f, 0.35f)
}


@Composable
fun IBDarkColorsPreview(darkColors: IBColors) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            ColorItemView(color = darkColors.primary1Default, name = "Primary 1 Default")
            ColorItemView(color = darkColors.primary1Hover, name = "Primary 1 Hover")
            ColorItemView(color = darkColors.primary1Pressed, name = "Primary 1 Pressed")
            ColorItemView(color = darkColors.primary1Disabled, name = "Primary 1 Disabled")
            Spacer(modifier = Modifier.height(16.dp))
            ColorItemView(color = darkColors.primary2Default, name = "Primary 2 Default")
            ColorItemView(color = darkColors.primary2Hover, name = "Primary 2 Hover")
            ColorItemView(color = darkColors.primary2Pressed, name = "Primary 2 Pressed")
            ColorItemView(color = darkColors.primary2Disabled, name = "Primary 2 Disabled")
            Spacer(modifier = Modifier.height(16.dp))
            ColorItemView(color = darkColors.secondaryDefault, name = "Secondary Default")
            ColorItemView(color = darkColors.secondaryHover, name = "Secondary Hover")
            ColorItemView(color = darkColors.secondaryPressed, name = "Secondary Pressed")
            Spacer(modifier = Modifier.height(16.dp))
            ColorItemView(color = darkColors.content1, name = "Content 1")
            ColorItemView(color = darkColors.content2, name = "Content 2")
            Spacer(modifier = Modifier.height(16.dp))
            ColorItemView(color = darkColors.black, name = "Black")
            ColorItemView(color = darkColors.white, name = "White")
            Spacer(modifier = Modifier.height(16.dp))
            ColorItemView(color = darkColors.grey1, name = "Grey 1")
            ColorItemView(color = darkColors.grey2, name = "Grey 2")
            ColorItemView(color = darkColors.grey3, name = "Grey 3")
            ColorItemView(color = darkColors.grey4, name = "Grey 4")
            ColorItemView(color = darkColors.grey5, name = "Grey 5")
            ColorItemView(color = darkColors.grey6, name = "Grey 6")
            Spacer(modifier = Modifier.height(16.dp))
            ColorItemView(color = darkColors.danger, name = "Danger")
            ColorItemView(color = darkColors.warning, name = "Warning")
            ColorItemView(color = darkColors.success, name = "Success")
        }
    }
}


@Preview
@Composable
fun IBDarkColorsPreviewPreview() {
    val darkColors = IBDarkColors()

    IBDarkColorsPreview(darkColors = darkColors)
}