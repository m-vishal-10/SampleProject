package com.icebrekr.icebrekrui.uielements.toast

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.icebrekr.icebrekrui.configurables.FontType
import com.icebrekr.icebrekrui.configurables.IBCustomFonts
import com.icebrekr.icebrekrui.configurables.IBFonts
import com.icebrekr.icebrekrui.configurables.color.IBTheme
import com.icebrekr.icebrekrui.helper.PackageImage
import com.icebrekr.icebrekrui.uielements.label.PrimaryLabel
import com.icebrekr.uicomponents.components.toast.LocalScreenWidth
import com.icebrekr.uicomponents.components.toast.ToastView

@Composable
fun TertiaryToastView(message: String, blockInteraction: Boolean = false) {
    ToastView(
        backgroundColor = IBTheme.theme.black.copy(alpha = 0.5f),
        leadingIcon = {
            Icon(painter = painterResource(id = PackageImage.info.toImage()),
                tint = Color.White, contentDescription ="Info")
        },
        message = {
            Box(
                Modifier.padding(8.dp)
            ) {
                PrimaryLabel(
                    text= message,
                    font= IBFonts.appliedFont(IBCustomFonts.regular, FontType.footnote),
                    foregroundColor= IBTheme.theme.white,
                    lineLimit = 3
                )
            }
        },
        dismissButton = {

        },
        blockBackgroundInteraction = blockInteraction
    )
}