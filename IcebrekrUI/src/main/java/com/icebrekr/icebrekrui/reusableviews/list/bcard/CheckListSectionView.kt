package com.icebrekr.icebrekrui.reusableviews.list.bcard

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icebrekr.icebrekrui.configurables.FontType
import com.icebrekr.icebrekrui.configurables.IBCustomFonts
import com.icebrekr.icebrekrui.configurables.IBFonts
import com.icebrekr.icebrekrui.configurables.color.IBTheme
import com.icebrekr.icebrekrui.helper.PackageImage
import com.icebrekr.icebrekrui.moduleviews.leadgeneration.businesscardreader.review.model.BCardProgressStateModel
import com.icebrekr.icebrekrui.uielements.label.PrimaryLabel
import com.icebrekr.uicomponents.components.buttons.ImageButton

//region Check List Section View
@Composable
fun CheckListSectionView(
    text: String,
    isChecked: MutableState<Boolean>,
    currentStep: MutableState<BCardProgressStateModel>,
    selectedIndices: MutableState<MutableSet<Int>>,
    onCheckedChange: (Boolean) -> Unit
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .border(
                BorderStroke(1.dp, IBTheme.theme.primary1Default),
                RoundedCornerShape(16.dp)
            )
    ) {

        Box(
            modifier = Modifier
                .padding(12.dp)
                .weight(0.6f)
        ) {
            PrimaryLabel(
                text = text,
                font = IBFonts.appliedFont(
                    customFont = IBCustomFonts.bold,
                    type = FontType.callout
                ),
                foregroundColor = IBTheme.theme.primary1Default,
                lineLimit = 3
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Box(
            modifier = Modifier.padding(12.dp)
        ) {
            ImageButton(
                image = if (isChecked.value) PackageImage.checkMark.toImage() else PackageImage.checkNo.toImage(),
                dimension = Pair(20f, 20f),
                foregroundColor = Color.Unspecified,
                onTap = {
                    if (isChecked.value) {
                        isChecked.value = !isChecked.value
                        onCheckedChange(isChecked.value)
                    } else {
                        var maxCount = 2
                        maxCount = when (currentStep.value) {
                            BCardProgressStateModel.LINKS -> {
                                3
                            }

                            else -> {
                                2
                            }
                        }
                        if (selectedIndices.value.size != maxCount) {
                            isChecked.value = !isChecked.value
                            onCheckedChange(isChecked.value)
                        }
                    }
                }
            )
        }

    }

}
//endregion

//region Preview
@Preview
@Composable
fun CheckListSectionViewPreview() {
    val isChecked = remember {
        mutableStateOf(true)
    }
    val isNotChecked = remember {
        mutableStateOf(false)
    }
    val currentStep = remember {
        mutableStateOf(BCardProgressStateModel.EMAIL)
    }
    val selectedIndices = remember {
        mutableStateOf(mutableSetOf(1))
    }
    Column {
        CheckListSectionView(
            text = "vijay@gmail.com",
            isChecked = isChecked,
            currentStep,
            selectedIndices,
        ) {

        }
        Spacer(modifier = Modifier.height(8.dp))
        CheckListSectionView(
            text = "vijay@gmail.com",
            isChecked = isNotChecked,
            currentStep,
            selectedIndices,
        ) {

        }
    }
}
//endregion