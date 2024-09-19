package com.icebrekr.icebrekrui.uielements.toast

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icebrekr.icebrekrui.configurables.FontType
import com.icebrekr.icebrekrui.configurables.IBCustomFonts
import com.icebrekr.icebrekrui.configurables.IBFonts
import com.icebrekr.icebrekrui.configurables.color.IBTheme
import com.icebrekr.icebrekrui.helper.PackageImage
import com.icebrekr.icebrekrui.uielements.button.custom.Tertiary2Button
import com.icebrekr.icebrekrui.uielements.label.PrimaryLabel
import com.icebrekr.uicomponents.components.buttons.properties.ButtonState
import com.icebrekr.uicomponents.components.toast.LocalScreenWidth
import com.icebrekr.uicomponents.components.toast.ToastView

@Composable
fun PrimaryToastView(message: String, buttonText: String = "Dismiss", action: (() -> Unit)? = null) {
    ToastView(
        backgroundColor = IBTheme.theme.black.copy(alpha = 0.5f),
        leadingIcon = {
            Icon(painter = painterResource(id = PackageImage.info.toImage()),
                tint = Color.White, contentDescription ="Info")
        },
        message = {
            Box(
                Modifier.width((LocalScreenWidth() * 0.6f).dp)
            ) {
                PrimaryLabel(
                    text = message,
                    font = IBFonts.appliedFont(IBCustomFonts.regular, FontType.footnote),
                    foregroundColor = IBTheme.theme.white
                )
            }
        },
        dismissButton = {
            Box(
                Modifier
                    .width(150.dp)
                    .padding(end = 8.dp)
                    .clickable {
                        action?.invoke()
                    }
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Spacer(Modifier.weight(1f))
                    PrimaryLabel(
                        text = buttonText,
                        font = IBFonts.appliedFont(IBCustomFonts.bold, FontType.footnote),
                        foregroundColor = IBTheme.theme.danger
                    )
                }
            }
        }
    )
}

@Preview
@Composable
private fun ToastPReview() {
    PrimaryToastView(message = "Contact Added Successfully SuccessfullySuccessfully", buttonText = "Dismiss Dismiss")
}