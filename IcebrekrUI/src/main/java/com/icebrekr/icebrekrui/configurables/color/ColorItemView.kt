package com.icebrekr.icebrekrui.configurables.color

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp

@Composable
fun ColorItemView(color: Color, name: String, textForegroundColor: Color = Color.Black) {
    Column(
        modifier = Modifier.padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.CenterHorizontally)
                .background(color = color, shape = RectangleShape)
        )
        Text(
            text = name,
            color = textForegroundColor,
            fontStyle = FontStyle.Normal,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}