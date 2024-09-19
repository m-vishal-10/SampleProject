package com.icebrekr.uicomponents.components.toast

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties


@Composable
fun ToastView(
    backgroundColor: Color,
    leadingIcon: @Composable () -> Unit,
    message: @Composable () -> Unit,
    dismissButton: @Composable () -> Unit,
    blockBackgroundInteraction: Boolean = false
) {
    if (blockBackgroundInteraction) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = { /* Do nothing to block clicks */ }
                )
                .padding(10.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            ToastContent(backgroundColor, leadingIcon, message, dismissButton)
        }
    } else {
        // If interaction should not be blocked
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            ToastContent(backgroundColor, leadingIcon, message, dismissButton)
        }
    }
}

@Composable
private fun ToastContent(
    backgroundColor: Color,
    leadingIcon: @Composable () -> Unit,
    message: @Composable () -> Unit,
    dismissButton: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 80.dp)
            .background(backgroundColor, shape = RoundedCornerShape(8.dp))
            .padding(8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            leadingIcon()
            Spacer(Modifier.width(8.dp))
            message()
            Spacer(Modifier.width(8.dp))
            dismissButton()
        }
    }
}


@Composable
fun ToastContentView() {
    var isShowing by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
        Button(
            onClick = { isShowing = true },
            modifier = Modifier.align(Alignment.TopCenter)
        ) {
            Text(text = "Show Toast")
        }

        if (isShowing) {
            Popup(
                alignment = Alignment.TopCenter,
                properties = PopupProperties(focusable = false)
            ) {
                ToastView(
                    backgroundColor = Color.Black.copy(alpha = 0.5f),
                    leadingIcon = {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    },
                    message = {
                        Box(
                            Modifier.width((LocalScreenWidth() * 0.6f).dp)
                        ) {
                            Text(
                                text = "Connection Removed Connection Removed Connection Removed Connection Removed Connection Removed Connection Removed ",
                                color = Color.White,
                            )
                        }
                    },
                    dismissButton = {
                        Text(
                            text = "Dismiss",
                            color = Color.Red,
                            modifier = Modifier.clickable { isShowing = false }
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun LocalScreenWidth(): Float {
    val screenWidth =
        LocalContext.current.resources.displayMetrics.widthPixels / LocalDensity.current.density
    return remember(screenWidth) { screenWidth }
}


@Preview
@Composable
fun ToastContentViewPreview() {
    MaterialTheme {
        ToastContentView()
    }
}
