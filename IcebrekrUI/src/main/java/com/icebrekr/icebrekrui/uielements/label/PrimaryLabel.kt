package com.icebrekr.icebrekrui.uielements.label

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icebrekr.icebrekrui.configurables.FontType
import com.icebrekr.icebrekrui.configurables.IBCustomFonts
import com.icebrekr.icebrekrui.configurables.IBFonts
import com.icebrekr.icebrekrui.configurables.color.IBTheme
import com.icebrekr.uicomponents.FontCustom
import com.icebrekr.uicomponents.components.labels.BaseLabel

@Composable
fun PrimaryLabel(
    text: String,
    font: FontCustom,
    foregroundColor: Color,
    textAlign: TextAlign = TextAlign.Start,
    lineLimit: Int? = null
) {
    BaseLabel(
        text = text,
        font = font,
        foregroundColor = foregroundColor,
        textAlign = textAlign,
        lineLimit = lineLimit
    )
}

@Preview(showBackground = true)
@Composable
fun PrimaryLabelPreview() {
    Column {
        PrimaryLabel(text = "Default Text Default Text Default Text Default Text", font = IBFonts.appliedFont(IBCustomFonts.blackItalic, FontType.TitleBold1
        ), foregroundColor = Color.Black)
        PrimaryLabel(text = "Custom Text", font = IBFonts.appliedFont(IBCustomFonts.blackItalic, FontType.bodyBold), foregroundColor = Color.Blue)
        PrimaryLabel(text = "Aligned Text", font = IBFonts.appliedFont(IBCustomFonts.regular, FontType.TitleBold1), foregroundColor = Color.Green, textAlign = TextAlign.Center)
    }
}
