package com.icebrekr.uicomponents.components.progress

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color

// MARK: - ProgressBarView
@Composable
fun ProgressBarView(
    backgroundColor: Color = Color.Black.copy(alpha = 0.4f),
    progressColor: Color = Color.White
) {
    Box(
        modifier = Modifier.fillMaxSize()
            .background(backgroundColor),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            color = progressColor,
            modifier = Modifier.scale(scaleX = 1.5f, scaleY = 1.5f)
        )
    }
}