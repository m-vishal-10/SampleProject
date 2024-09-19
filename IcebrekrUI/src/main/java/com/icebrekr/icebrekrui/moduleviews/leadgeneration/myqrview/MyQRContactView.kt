package com.icebrekr.icebrekrui.moduleviews.leadgeneration.myqrview

import ClickableLabel
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icebrekr.icebrekrui.configurables.FontType
import com.icebrekr.icebrekrui.configurables.IBCustomFonts
import com.icebrekr.icebrekrui.configurables.IBFonts
import com.icebrekr.icebrekrui.configurables.color.IBTheme
import com.icebrekr.icebrekrui.helper.PackageImage
import com.icebrekr.icebrekrui.uielements.label.PrimaryLabel
import com.icebrekr.uicomponents.components.buttons.ImageButton

//region MyQRContactView
@Composable
fun MyQRContactView(
    title: String,
    urlString: String,
    openAction: (() -> Unit)?,
    copyAction: (() -> Unit)?,
    shareAction: (() -> Unit)?
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(width = 1.dp, color = IBTheme.theme.grey4)
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .background(IBTheme.theme.white)
                .padding(16.dp)
        ) {
            PrimaryLabel(
                text = title,
                font = IBFonts.appliedFont(
                    customFont = IBCustomFonts.bold,
                    type = FontType.title3
                ),
                foregroundColor = IBTheme.theme.primary1Default,
                lineLimit = 1
            )
            ClickableLabel(
                fullText = urlString,
                subText = urlString,
                font = IBFonts.appliedFont(
                    customFont = IBCustomFonts.regular,
                    type = FontType.caption1
                ),
                textColor = IBTheme.theme.primary1Default,
                boldUnderlineColor = IBTheme.theme.primary1Default,
                lineLimit = 1,
                textAlignment = TextAlign.Left
            )
            InfoView(
                openAction = openAction,
                copyAction = copyAction,
                shareAction = shareAction,
            )
        }
    }
}
//endregion

//region InfoView
@Composable
private fun InfoView(
    openAction: (() -> Unit)?,
    copyAction: (() -> Unit)?,
    shareAction: (() -> Unit)?,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        CreateActionView(
            image = PackageImage.open.toImage(),
            title = "Open now",
            action = openAction
        )
        Spacer(modifier = Modifier.weight(1f))
        CreateActionView(
            image = PackageImage.copy.toImage(),
            title = "Copy",
            action = copyAction
        )
        Spacer(modifier = Modifier.weight(1f))
        CreateActionView(
            image = PackageImage.share.toImage(),
            title = "Share",
            action = shareAction
        )
    }
}
//endregion

//region CreateActionView
@Composable
fun CreateActionView(
    image: Int,
    title: String,
    action: (() -> Unit)?
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.clickable { action?.invoke() }
    ) {
        ImageButton(
            image = image,
            dimension = Pair(16f, 16f),
            onTap = {
                action?.invoke()
            }
        )
        Spacer(modifier = Modifier.width(8.dp))
        PrimaryLabel(
            text = title,
            font = IBFonts.appliedFont(customFont = IBCustomFonts.medium, type = FontType.caption1),
            foregroundColor = IBTheme.theme.primary1Default
        )
    }
}
//endregion

//region Preview
@Preview
@Composable
fun MyQRContactViewPreview() {
    val context = LocalContext.current
    MyQRContactView(
        title = "QR Url",
        urlString = "https://icebrekr.com/contact/rohit",
        openAction = {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://icebrekr.com/contact/rohit"))
            context.startActivity(intent)
        },
        copyAction = {
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Copied URL", "https://icebrekr.com/contact/rohit")
            clipboard.setPrimaryClip(clip)
        },
        shareAction = {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, "https://icebrekr.com/contact/rohit")
            }
            context.startActivity(Intent.createChooser(intent, "Share URL"))
        }
    )
}
//endregion