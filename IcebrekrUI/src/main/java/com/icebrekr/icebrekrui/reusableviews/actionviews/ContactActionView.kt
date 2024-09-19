package com.icebrekr.icebrekrui.reusableviews.actionviews

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.icebrekr.icebrekrui.configurables.color.IBTheme
import com.icebrekr.icebrekrui.helper.PackageImage
import com.icebrekr.icebrekrui.uielements.button.custom.TertiaryButton
import com.icebrekr.uicomponents.components.buttons.ImageButton
import com.icebrekr.uicomponents.components.buttons.properties.ButtonState

enum class ContactActionState {
    SelectAll, UnSelectAll, Export, Delete
}

@Composable
fun ContactActionView(
    isSelected: MutableState<Boolean>,
    action: ((ContactActionState) -> Unit)
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(IBTheme.theme.grey4),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.weight(0.5f),
            contentAlignment = Alignment.Center
        ) {
            TertiaryButton(
                titleText = if (isSelected.value) "Unselect All" else "Select All",
                buttonState = ButtonState.DEFAULT,
                action = {
                    action?.invoke(if (isSelected.value) ContactActionState.UnSelectAll else ContactActionState.SelectAll)
                }
            )
        }
        Box(
            modifier = Modifier.weight(0.4f),
            contentAlignment = Alignment.Center
        ) {
            TertiaryButton(
                titleText = "Export",
                buttonState = ButtonState.DEFAULT,
                leftIcon = PackageImage.export.toImage(),
                action = {
                    action?.invoke(ContactActionState.Export)
                }
            )
        }
        Box(
            modifier = Modifier.weight(0.15f),
            contentAlignment = Alignment.Center
        ) {
            ImageButton(
                image = PackageImage.delete.toImage(),
                foregroundColor = IBTheme.theme.secondaryDefault,
                dimension = Pair(20f, 20f)
            ) {
                action?.invoke(ContactActionState.Delete)
            }
        }
    }
}

@Preview
@Composable
fun ActionViewPreview() {
    val selected = remember {
        mutableStateOf(false)
    }
    ContactActionView(isSelected = selected , action = {

    })
}