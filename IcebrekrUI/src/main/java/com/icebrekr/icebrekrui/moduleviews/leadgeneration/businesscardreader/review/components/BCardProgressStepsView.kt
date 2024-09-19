package com.icebrekr.icebrekrui.moduleviews.leadgeneration.businesscardreader.review.components

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icebrekr.icebrekrui.configurables.FontType
import com.icebrekr.icebrekrui.configurables.IBCustomFonts
import com.icebrekr.icebrekrui.configurables.IBFonts
import com.icebrekr.icebrekrui.configurables.color.IBTheme
import com.icebrekr.icebrekrui.moduleviews.leadgeneration.businesscardreader.review.model.BCardProgressStateModel
import com.icebrekr.icebrekrui.uielements.label.PrimaryLabel
import com.icebrekr.uicomponents.extensions.view.bordered

//region BCard Progress Steps View
@Composable
fun BCardProgressStepsView(
    currentStep: MutableState<BCardProgressStateModel>,
    enableNext: MutableState<Boolean>,
    doneAction: (() -> Unit)? = null
) {
    Box {
        Column(
            modifier = Modifier
                .background(color = IBTheme.theme.grey6)
                .bordered(
                    cornerRadius = 5.dp,
                    borderWidth = 1.dp,
                    borderColor = IBTheme.theme.grey6
                )
                .padding(16.dp)
        ) {

            ProgressStepsView(
                currentStep = currentStep.value.ordinal,
                totalSteps = BCardProgressStateModel.values().size
            )

            ContentView(
                currentStep = currentStep.value,
                enableNext = enableNext,
                doneAction = doneAction
            )
        }
    }
}
//endregion

//region ContentView
@Composable
private fun ContentView(
    currentStep: BCardProgressStateModel,
    enableNext: MutableState<Boolean>,
    doneAction: (() -> Unit)?
) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        PrimaryLabel(
            text = currentStep.info.title,
            font = IBFonts.appliedFont(customFont = IBCustomFonts.bold, type = FontType.title3),
            foregroundColor = IBTheme.theme.primary1Default
        )
        BottomView(
            currentStep = currentStep,
            enableNext = enableNext,
            doneAction = doneAction
        )
    }
}
//endregion

//region BottomView
@Composable
private fun BottomView(
    currentStep: BCardProgressStateModel,
    enableNext: MutableState<Boolean>,
    doneAction: (() -> Unit)?
) {
    var buttonClicked = false

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        currentStep.info.subTitle?.let { subTitle ->
            PrimaryLabel(
                text = subTitle,
                font = IBFonts.appliedFont(
                    customFont = IBCustomFonts.regular,
                    type = FontType.body
                ),
                foregroundColor = IBTheme.theme.primary2Default
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        DoneButtonView(
            text = currentStep.info.doneText,
            enableNext = enableNext,
            onClick = {
                if (currentStep.info.doneText == "Save") {
                    if (!buttonClicked) {
                        doneAction?.invoke()
                    }
                    buttonClicked = true
                    Handler(Looper.getMainLooper()).postDelayed(
                        {
                            buttonClicked = false
                        }, 2000
                    )
                } else {
                    doneAction?.invoke()
                }
            }
        )
    }
}
//endregion

//region DoneButtonView
@Composable
private fun DoneButtonView(
    text: String,
    enableNext: MutableState<Boolean>,
    onClick: (() -> Unit)
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable {
            onClick.invoke()
        }
    ) {
        PrimaryLabel(
            text = text,
            font = IBFonts.appliedFont(customFont = IBCustomFonts.bold, type = FontType.callout),
            foregroundColor = if (enableNext.value) IBTheme.theme.primary1Default else IBTheme.theme.primary1Disabled
        )
        if (enableNext.value) {
            Box(
                modifier = Modifier.padding(start = 8.dp)
            ) {
                PrimaryLabel(
                    text = ">",
                    font = IBFonts.appliedFont(
                        customFont = IBCustomFonts.bold,
                        type = FontType.callout
                    ),
                    foregroundColor = IBTheme.theme.primary1Default
                )
            }
        }
    }
}
//endregion

//region Preview
@Preview
@Composable
fun BCardProgressStepsViewPreview() {
    val currentStep = remember {
        mutableStateOf(BCardProgressStateModel.EMAIL)
    }
    val enableNext = remember {
        mutableStateOf(false)
    }
    Column {
        BCardProgressStepsView(
            currentStep = currentStep,
            enableNext = enableNext
        )
        Spacer(modifier = Modifier.height(16.dp))
        BCardProgressStepsView(
            currentStep = currentStep,
            enableNext = enableNext
        )
    }
}
//endregion