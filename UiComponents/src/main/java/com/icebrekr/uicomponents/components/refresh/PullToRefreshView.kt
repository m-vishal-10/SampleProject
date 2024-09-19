package com.icebrekr.uicomponents.components.refresh

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun PullToRefreshView(
    backgroundColor: Color = Color.White,
    foregroundColor: Color = Color.Black,
    isEnabled: Boolean = true,
    onRefresh: (() -> Unit)
) {
    var isRefreshing by remember { mutableStateOf(false) }
    var isRefreshIndicatorVisible by remember { mutableStateOf(false) }

    LaunchedEffect(isEnabled) {
        if (isEnabled && !isRefreshing) {
            isRefreshing = true
            isRefreshIndicatorVisible = true
            onRefresh.invoke()
            delay(2000) // Simulating a delay for demonstration purposes
            isRefreshIndicatorVisible = false
            isRefreshing = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Spacer(
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()
        )

        if (isRefreshIndicatorVisible) {
            CircularProgressIndicator(
                color = foregroundColor,
                strokeWidth = ProgressIndicatorDefaults.StrokeWidth
            )
        }
    }
}
