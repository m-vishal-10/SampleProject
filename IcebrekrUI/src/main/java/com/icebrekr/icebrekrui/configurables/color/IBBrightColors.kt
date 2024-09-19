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

class IBBrightColors : IBColors {
    override val primary1Default: Color = Color(0.11f, 0.22f, 0.45f)
    override val primary1Hover: Color = Color(0.15f, 0.31f, 0.61f)
    override val primary1Pressed: Color = Color(0.07f, 0.14f, 0.29f)
    override val primary1Disabled: Color = Color(0.85f, 0.85f, 0.85f)

    override val primary2Default: Color = Color(1f, 0.16f, 0.38f)
    override val primary2Hover: Color = Color(1f, 0.36f, 0.53f)
    override val primary2Pressed: Color = Color(0.96f, 0f, 0.25f)
    override val primary2Disabled: Color = Color(0.5f, 0.5f, 0.5f)

    override val secondaryDefault: Color = Color(1.0f, 0.0f, 0.0f)
    override val secondaryHover: Color = Color(0.42f, 0.4f, 0.5f)
    override val secondaryPressed: Color = Color(0.24f, 0.22f, 0.28f)

    override val content1: Color = Color(0f, 0f, 0f)
    override val content2: Color = Color(1f, 1f, 1f)

    override val black: Color = Color(0f, 0f, 0f)
    override val white: Color = Color(1f, 1f, 1f)

    override val grey1: Color = Color(0.56f, 0.56f, 0.58f)
    override val grey2: Color = Color(0.68f, 0.68f, 0.7f)
    override val grey3: Color = Color(0.78f, 0.78f, 0.8f)
    override val grey4: Color = Color(0.82f, 0.82f, 0.84f)
    override val grey5: Color = Color(0.9f, 0.9f, 0.92f)
    override val grey6: Color = Color(0.95f, 0.95f, 0.97f)

    override val danger: Color = Color(0.92f, 0.05f, 0f)
    override val warning: Color = Color(1f, 0.58f, 0f)
    override val success: Color = Color(0.2f, 0.78f, 0.35f)
}

@Composable
fun IBBrightColorsPreview(brightColors: IBColors) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            ColorItemView(color = brightColors.primary1Default, name = "Primary 1 Default")
            ColorItemView(color = brightColors.primary1Hover, name = "Primary 1 Hover")
            ColorItemView(color = brightColors.primary1Pressed, name = "Primary 1 Pressed")
            ColorItemView(color = brightColors.primary1Disabled, name = "Primary 1 Disabled")
            Spacer(modifier = Modifier.height(16.dp))
            ColorItemView(color = brightColors.primary2Default, name = "Primary 2 Default")
            ColorItemView(color = brightColors.primary2Hover, name = "Primary 2 Hover")
            ColorItemView(color = brightColors.primary2Pressed, name = "Primary 2 Pressed")
            ColorItemView(color = brightColors.primary2Disabled, name = "Primary 2 Disabled")
            Spacer(modifier = Modifier.height(16.dp))
            ColorItemView(color = brightColors.secondaryDefault, name = "Secondary Default")
            ColorItemView(color = brightColors.secondaryHover, name = "Secondary Hover")
            ColorItemView(color = brightColors.secondaryPressed, name = "Secondary Pressed")
            Spacer(modifier = Modifier.height(16.dp))
            ColorItemView(color = brightColors.content1, name = "Content 1")
            ColorItemView(color = brightColors.content2, name = "Content 2")
            Spacer(modifier = Modifier.height(16.dp))
            ColorItemView(color = brightColors.black, name = "Black")
            ColorItemView(color = brightColors.white, name = "White")
            Spacer(modifier = Modifier.height(16.dp))
            ColorItemView(color = brightColors.grey1, name = "Grey 1")
            ColorItemView(color = brightColors.grey2, name = "Grey 2")
            ColorItemView(color = brightColors.grey3, name = "Grey 3")
            ColorItemView(color = brightColors.grey4, name = "Grey 4")
            ColorItemView(color = brightColors.grey5, name = "Grey 5")
            ColorItemView(color = brightColors.grey6, name = "Grey 6")
            Spacer(modifier = Modifier.height(16.dp))
            ColorItemView(color = brightColors.danger, name = "Danger")
            ColorItemView(color = brightColors.warning, name = "Warning")
            ColorItemView(color = brightColors.success, name = "Success")
        }
    }
}


@Preview
@Composable
fun IBBrightColorsPreviewPreview() {
    val brightColors = IBBrightColors()

    IBBrightColorsPreview(brightColors = brightColors)
}