package com.icebrekr.icebrekrui.reusableviews.imagepicker

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.icebrekr.icebrekrui.configurables.FontType
import com.icebrekr.icebrekrui.configurables.IBCustomFonts
import com.icebrekr.icebrekrui.configurables.IBFonts
import com.icebrekr.icebrekrui.configurables.color.IBTheme
import com.icebrekr.icebrekrui.uielements.label.PrimaryLabel
import kotlinx.coroutines.launch

//region ImagePicker
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ImagePicker(
    showActionSheet: MutableState<Boolean>,
    onCameraOptionSelected: (() -> Unit),
    onGalleryOptionSelected: (() -> Unit),
    content: @Composable () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )
    val scope = rememberCoroutineScope()

    LaunchedEffect(showActionSheet.value) {
        if (showActionSheet.value) {
            scope.launch { sheetState.show() }
        } else {
            scope.launch { sheetState.hide() }
        }
    }
    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(Color.White)
            ) {
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .clickable {
                                showActionSheet.value = false
                                onCameraOptionSelected.invoke()
                            }
                    ) {
                        PrimaryLabel(
                            text = "Camera",
                            font = IBFonts.appliedFont(IBCustomFonts.regular, FontType.callout),
                            foregroundColor = IBTheme.theme.primary1Default
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Box(
                        modifier = Modifier
                            .clickable {
                                showActionSheet.value = false
                                onGalleryOptionSelected.invoke()
                            }
                    ) {
                        PrimaryLabel(
                            text = "Photo Library",
                            font = IBFonts.appliedFont(IBCustomFonts.regular, FontType.callout),
                            foregroundColor = IBTheme.theme.primary1Default
                        )
                    }

                }
            }
        }
    ) {
        content()
    }
}
//endregion