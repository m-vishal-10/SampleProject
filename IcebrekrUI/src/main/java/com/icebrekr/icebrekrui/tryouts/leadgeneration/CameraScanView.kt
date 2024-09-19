package com.icebrekr.icebrekrui.tryouts.leadgeneration

import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.icebrekr.icebrekrui.R
import com.icebrekr.icebrekrui.configurables.FontType
import com.icebrekr.icebrekrui.configurables.IBCustomFonts
import com.icebrekr.icebrekrui.configurables.IBFonts
import com.icebrekr.icebrekrui.configurables.color.IBTheme
import com.icebrekr.icebrekrui.moduleviews.leadgeneration.businesscardreader.review.views.BusinessCardReaderView
import com.icebrekr.icebrekrui.moduleviews.leadgeneration.myqrview.MyQRCodeContactView
import com.icebrekr.icebrekrui.reusableviews.qrcode.QRCodeView
import com.icebrekr.icebrekrui.uielements.label.PrimaryLabel
import com.icebrekr.icebrekrui.uielements.tab.CustomTabBarContentView
import com.icebrekr.sharedresource.sharedmodels.contact.MyQRContactModel
import com.icebrekr.uicomponents.components.buttons.ImageButton

//region CameraScanAction
sealed class CameraScanAction {
    object Back : CameraScanAction()
    object QrView : CameraScanAction()
    data class QrScanned(val qrCode: String) : CameraScanAction()
    data class QrBrowserAction(val urlString: String?) : CameraScanAction()
    data class QrCopyAction(val urlString: String?) : CameraScanAction()
    data class QrShareAction(val urlString: String?) : CameraScanAction()
    data class BCardImage(val bitmap: Bitmap) : CameraScanAction()
    data class BCardAutoFocusToggle(val isOn: Boolean) : CameraScanAction()
}
//endregion

//region CameraScanView
@Composable
fun CameraScanView(
    hasPermission: MutableState<Boolean>,
    isPresentingScanner: MutableState<Boolean>,
    selectedTab: MutableState<Int?>,
    isCameraFlashOn: MutableState<Boolean>,
    businessCardAutoFocus: MutableState<Boolean?>,
    myQRContactModel: State<MyQRContactModel>,
    action: ((CameraScanAction) -> Unit)
) {

    LaunchedEffect(businessCardAutoFocus.value) {
        businessCardAutoFocus.value?.let {
            action.invoke(CameraScanAction.BCardAutoFocusToggle(businessCardAutoFocus.value!!))
        }
    }

    Scaffold(
        topBar = {
            HeaderView(
                action,
                isCameraFlashOn,
                selectedTab
            )
        },
        backgroundColor = Color.Black,
        content = {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Box(
                    modifier = Modifier.weight(0.85f)
                ) {
                    when (selectedTab.value) {
                        0 -> {
                            CameraScanContent(
                                it,
                                hasPermission,
                                action,
                                isPresentingScanner,
                                isCameraFlashOn
                            )
                        }

                        1 -> {
                            BusinessCardReaderContent(
                                hasPermission = hasPermission,
                                action = action,
                                isCameraFlashOn = isCameraFlashOn,
                                isAutoFocusEnabled = businessCardAutoFocus
                            )
                        }

                        2 -> {
                            MyQRCodeContactView(
                                qrInfo = myQRContactModel,
                                openAction = {
                                    action.invoke(
                                        CameraScanAction.QrBrowserAction(myQRContactModel.value.urlString)
                                    )
                                },
                                copyAction = {
                                    action.invoke(
                                        CameraScanAction.QrCopyAction(myQRContactModel.value.urlString)
                                    )
                                },
                                shareAction = {
                                    action.invoke(
                                        CameraScanAction.QrShareAction(myQRContactModel.value.urlString)
                                    )
                                },
                                openContactAction = {
                                    action.invoke(
                                        CameraScanAction.QrView
                                    )
                                }
                            )
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .weight(0.15f)
                        .padding(bottom = 24.dp)
                ) {
                    CustomTabBarContentView(selectedTab = selectedTab)
                }
            }
        }
    )
}

@Composable
fun CameraScanContent(
    paddingValues: PaddingValues,
    hasPermission: MutableState<Boolean>,
    action: (CameraScanAction) -> Unit,
    isPresentingScanner: MutableState<Boolean>,
    isCameraFlashOn: MutableState<Boolean>
) {
    val modifier = Modifier.fillMaxSize()
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        QRCodeView(
            onQrCodeScanned = {
                action.invoke(CameraScanAction.QrScanned(it))
            },
            hasCameraPermission = hasPermission,
            isPresentingScanner = isPresentingScanner,
            isCameraFlashOn = isCameraFlashOn
        )
    }
}

@Composable
fun BusinessCardReaderContent(
    hasPermission: MutableState<Boolean>,
    action: (CameraScanAction) -> Unit,
    isCameraFlashOn: MutableState<Boolean>,
    isAutoFocusEnabled: MutableState<Boolean?>
) {
    val modifier = Modifier.fillMaxSize()
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        BusinessCardReaderView(
            isAutoFocusEnabled = isAutoFocusEnabled,
            imageCompletionAction = {
                action.invoke(CameraScanAction.BCardImage(it))
            },
            hasCameraPermission = hasPermission,
            isCameraFlashOn = isCameraFlashOn
        )
    }
}

@Composable
fun HeaderView(
    action: (CameraScanAction) -> Unit,
    isCameraFlashOn: MutableState<Boolean>,
    selectedTab: MutableState<Int?>
) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.Black),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        ImageButton(
            image = R.drawable.ic_close_button,
            foregroundColor = IBTheme.theme.white,
            dimension = Pair(24f, 24f)
        ) {
            action.invoke(CameraScanAction.Back)
        }
        Spacer(modifier = Modifier.weight(1f))
        PrimaryLabel(
            text = "Scan",
            font = IBFonts.appliedFont(IBCustomFonts.bold, FontType.title3),
            foregroundColor = IBTheme.theme.white
        )
        Spacer(modifier = Modifier.weight(1f))
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (selectedTab.value != 2 && context.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
                ImageButton(
                    image = if (isCameraFlashOn.value) R.drawable.ic_flash_on else R.drawable.ic_flash_off,
                    foregroundColor = if (isCameraFlashOn.value) Color(0xFFF3AF2D) else IBTheme.theme.white,
                    dimension = Pair(24f, 24f)
                ) {
                    isCameraFlashOn.value = !isCameraFlashOn.value
                }
            }
            Spacer(modifier = Modifier.width(24.dp))
            ImageButton(
                image = R.drawable.ic_share_contact,
                foregroundColor = IBTheme.theme.white,
                dimension = Pair(24f, 24f)
            ) {
                action.invoke(CameraScanAction.QrView)
            }
        }
    }
}
//endregion