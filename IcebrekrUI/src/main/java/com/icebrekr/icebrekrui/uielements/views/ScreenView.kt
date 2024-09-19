package com.icebrekr.icebrekrui.uielements.views

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.icebrekr.icebrekrui.configurables.color.IBTheme

//region Screen View - Generic for All UI
@Composable
fun <Content : @Composable () -> Unit> ScreenView(content: Content) {
    Surface(
        color = IBTheme.theme.white,
        modifier = Modifier.fillMaxSize()
    ) {
        content()
    }
}
//endregion

//region Screen View - Preview
@Preview
@Composable
fun ScreenPreview() {
    ScreenView {
        Text("Hello")
    }
}
//endregion