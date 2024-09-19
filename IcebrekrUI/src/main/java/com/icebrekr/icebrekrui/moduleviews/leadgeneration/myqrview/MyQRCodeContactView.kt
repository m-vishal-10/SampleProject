package com.icebrekr.icebrekrui.moduleviews.leadgeneration.myqrview

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.rememberAsyncImagePainter
import com.icebrekr.icebrekrui.configurables.FontType
import com.icebrekr.icebrekrui.configurables.IBCustomFonts
import com.icebrekr.icebrekrui.configurables.IBFonts
import com.icebrekr.icebrekrui.configurables.color.IBTheme
import com.icebrekr.icebrekrui.helper.PackageImage
import com.icebrekr.icebrekrui.uielements.imageview.URLImageView
import com.icebrekr.icebrekrui.uielements.label.PrimaryLabel
import com.icebrekr.sharedresource.sharedmodels.contact.MyQRContactModel
import com.icebrekr.uicomponents.components.buttons.ImageButton
import com.icebrekr.uicomponents.extensions.view.bordered
import kotlinx.coroutines.launch

//region My QR Code View
@Composable
fun MyQRCodeContactView(
    qrInfo: State<MyQRContactModel>,
    openAction: (() -> Unit)? = null,
    copyAction: (() -> Unit)? = null,
    shareAction: (() -> Unit)? = null,
    openContactAction: (() -> Unit)? = null
) {
    val showQRUrlInfo = remember { mutableStateOf(false) }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    var qrButtonClicked = false

    LaunchedEffect(showQRUrlInfo.value) {
        if (showQRUrlInfo.value) {
            coroutineScope.launch {
                listState.animateScrollToItem(listState.layoutInfo.totalItemsCount)
            }
        }
    }

    Surface(
        color = IBTheme.theme.black,
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            state = listState,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(0.dp)
                ) {
                    Card(
                        modifier = Modifier
                            .size(90.dp)
                            .padding(bottom = 4.dp)
                            .zIndex(1.0F)
                            .offset(y = 45.dp),
                        shape = RoundedCornerShape(45.dp),
                        border = BorderStroke(width = 5.dp, color = IBTheme.theme.primary1Default)
                    ) {
                        URLImageView(
                            urlString = qrInfo.value.profileImageUrl
                        )
                    }
                    if (qrInfo.value.qrCodeImage != null) {
                        Card(
                            modifier = Modifier
                                .size(250.dp)
                                .bordered(
                                    5.dp,
                                    5.dp,
                                    IBTheme.theme.primary1Default
                                )
                                .clickable {
                                    if (!qrButtonClicked) {
                                        openContactAction?.invoke()
                                    }
                                    qrButtonClicked = true
                                    Handler(Looper.getMainLooper()).postDelayed(
                                        {
                                            qrButtonClicked = false
                                        }, 2000
                                    )
                                },
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(qrInfo.value.qrCodeImage),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(250.dp)
                                    .background(Color.Transparent)
                            )
                        }
                    } else {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier.clickable {
                                if (!qrButtonClicked) {
                                    openContactAction?.invoke()
                                }
                                qrButtonClicked = true
                                Handler(Looper.getMainLooper()).postDelayed(
                                    {
                                        qrButtonClicked = false
                                    }, 2000
                                )
                            }
                        ) {
                            ImageButton(
                                image = PackageImage.qRCode.toImage(),
                                dimension = Pair(250f, 250f),
                                foregroundColor = Color.White,
                                onTap = {
                                    if (!qrButtonClicked) {
                                        openContactAction?.invoke()
                                    }
                                    qrButtonClicked = true
                                    Handler(Looper.getMainLooper()).postDelayed(
                                        {
                                            qrButtonClicked = false
                                        }, 2000
                                    )
                                }
                            )
                            PrimaryLabel(
                                text = "Tap to open your QR Contact",
                                font = IBFonts.appliedFont(
                                    customFont = IBCustomFonts.medium,
                                    type = FontType.callout
                                ),
                                foregroundColor = IBTheme.theme.content2,
                                lineLimit = 1
                            )
                        }
                    }
                }

                InfoView(
                    qrInfo = qrInfo.value,
                    onToggle = { showQRUrlInfo.value = !showQRUrlInfo.value })

                if (showQRUrlInfo.value) {
                    Box(modifier = Modifier.padding(vertical = 8.dp)) {
                        MyQRContactView(
                            title = "QR Url",
                            urlString = qrInfo.value.urlString ?: "",
                            openAction = openAction,
                            copyAction = copyAction,
                            shareAction = shareAction
                        )
                    }
                }
            }
        }
    }
}
//endregion

//region InfoView
@Composable
fun InfoView(
    qrInfo: MyQRContactModel,
    onToggle: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        PrimaryLabel(
            text = qrInfo.name,
            font = IBFonts.appliedFont(customFont = IBCustomFonts.bold, type = FontType.title),
            foregroundColor = IBTheme.theme.content2,
            lineLimit = 2,
            textAlign = TextAlign.Center
        )
        if (!qrInfo.title.isNullOrEmpty()) {
            PrimaryLabel(
                text = qrInfo.title,
                font = IBFonts.appliedFont(
                    customFont = IBCustomFonts.bold,
                    type = FontType.headerBold
                ),
                foregroundColor = IBTheme.theme.content2,
                lineLimit = 2,
                textAlign = TextAlign.Center
            )
        }
        if (qrInfo.urlString != null) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.clickable { onToggle() }
            ) {
                PrimaryLabel(
                    text = "QR Code URL",
                    font = IBFonts.appliedFont(
                        customFont = IBCustomFonts.bold,
                        type = FontType.caption1
                    ),
                    foregroundColor = IBTheme.theme.content2,
                    lineLimit = 1
                )
                ImageButton(
                    image = PackageImage.dropdown.toImage(),
                    foregroundColor = Color.White,
                    dimension = Pair(12f, 12f),
                    onTap = {
                        onToggle()
                    }
                )
            }
        }
    }
}
//endregion

//region Preview
@Preview(showBackground = true)
@Composable
fun MyQRCodeContactViewPreview() {
    val context = LocalContext.current

    val qrInfo = remember {
        mutableStateOf(
            MyQRContactModel(
                profileImageUrl = "https://images.unsplash.com/photo-1719702702556-43c793e2b625?q=80&w=3028&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                qrCodeImage = null,
                name = "Vijay",
                title = "Designer",
                urlString = "https://images.unsplash.com/photo-1719702702556-43c793e2b625?q=80&w=3028&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
            )
        )
    }

    MyQRCodeContactView(
        qrInfo = qrInfo,
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