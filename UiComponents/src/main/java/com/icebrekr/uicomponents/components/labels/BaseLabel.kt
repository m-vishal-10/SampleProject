package com.icebrekr.uicomponents.components.labels

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.icebrekr.uicomponents.FontCustom

// region Base Label
@Composable
fun BaseLabel(
    text: String,
    font: FontCustom,
    foregroundColor: Color,
    textAlign: TextAlign,
    lineLimit: Int? = null

) {
    BasicText(
        text = text,
        style = TextStyle(
            fontFamily = font.fontFamily,
            color = foregroundColor,
            fontSize = font.fontSize,
            textAlign = textAlign
        ),
        maxLines = lineLimit ?: 1,
        overflow = TextOverflow.Ellipsis
    )
}
//endregion


//region Base Label - Preview
@Preview(showBackground = true)
@Composable
fun PreviewBaseLabelVariations() {
    Column {
        // Example 1: Default font with large size, red color, center alignment
        BaseLabel(
            text = "Large Red Centered Text",
            font = FontCustom(FontFamily.Default, 18.sp),
            foregroundColor = Color.Red,
            textAlign = TextAlign.Center
        )
        // Example 2: Serif font with medium size, blue color, start alignment
        BaseLabel(
            text = "Medium Blue Start-Aligned Text",
            font = FontCustom(FontFamily.Default, 14.sp),
            foregroundColor = Color.Blue,
            textAlign = TextAlign.Start,

        )
        // Example 3: Sans-serif font with small size, green color, end alignment
        BaseLabel(
            text = "Small Green End-Aligned Text",
            font = FontCustom(FontFamily.Default, 12.sp),
            foregroundColor = Color.Green,
            textAlign = TextAlign.End
        )
    }
}

//endregion
