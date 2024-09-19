package com.icebrekr.icebrekrui.moduleviews.leadgeneration.businesscardreader.review.views

import NavigationAction
import NavigationHeaderView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icebrekr.icebrekrui.configurables.color.IBTheme
import com.icebrekr.icebrekrui.helper.PackageImage
import com.icebrekr.icebrekrui.moduleviews.leadgeneration.businesscardreader.review.components.BCardProgressStepsView
import com.icebrekr.icebrekrui.moduleviews.leadgeneration.businesscardreader.review.model.BCardProgressStateModel
import com.icebrekr.icebrekrui.reusableviews.list.bcard.CheckListSectionView
import com.icebrekr.sharedresource.sharedmodels.contact.UpdateContactModel
import com.icebrekr.utilities.validator.filteringEmail
import com.icebrekr.utilities.validator.filteringLink
import com.icebrekr.utilities.validator.filteringPhoneNumber

//region BCardContactReviewView
@Composable
fun BCardContactReviewView(
    contactModel: MutableState<UpdateContactModel>,
    capturedTexts: MutableState<List<String>>,
    cancelAction: (() -> Unit)? = null,
    doneAction: ((UpdateContactModel) -> Unit)? = null
) {

    val currentStep = remember { mutableStateOf(BCardProgressStateModel.NAME) }
    val selectedIndices = remember { mutableStateOf(mutableSetOf<Int>()) }

    LaunchedEffect(currentStep.value) {
        prepopulateForStep(currentStep, contactModel, capturedTexts, selectedIndices)
    }

    Surface(
        color = IBTheme.theme.white,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            BCardContactReviewHeaderView(
                currentStep = currentStep,
                cancelAction = cancelAction
            )
            BCardContactReviewMatchesView(
                currentStep = currentStep,
                contactModel = contactModel,
                capturedTexts = capturedTexts,
                selectedIndices = selectedIndices,
                doneAction = doneAction
            )
            BCardContactReviewFormView(
                currentStep = currentStep,
                capturedTexts = capturedTexts,
                selectedIndices = selectedIndices,
            )
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}
//endregion

//region BCardContactReviewHeaderView
@Composable
fun BCardContactReviewHeaderView(
    currentStep: MutableState<BCardProgressStateModel>,
    cancelAction: (() -> Unit)?
) {
    NavigationHeaderView(
        titleText = "Contact Review",
        backActionIcon = PackageImage.backChevron.toImage(),
        primaryActionIcon = PackageImage.closeBlue.toImage(),
        secondaryActionIcon = null,
        action = {
            when (it) {
                NavigationAction.backAction -> {
                    moveToPreviousStep(currentStep, cancelAction)
                }

                NavigationAction.primaryAction -> {
                    cancelAction?.invoke()
                }

                else -> {}
            }
        }
    )
}
//endregion

//region BCardContactReviewMatchesView
@Composable
fun BCardContactReviewMatchesView(
    currentStep: MutableState<BCardProgressStateModel>,
    contactModel: MutableState<UpdateContactModel>,
    capturedTexts: MutableState<List<String>>,
    selectedIndices: MutableState<MutableSet<Int>>,
    doneAction: ((UpdateContactModel) -> Unit)?
) {
    val enableText = remember {
        mutableStateOf(selectedIndices.value.isNotEmpty())
    }

    LaunchedEffect(selectedIndices.value) {
        enableText.value = selectedIndices.value.isNotEmpty()
    }

    BCardProgressStepsView(
        currentStep = currentStep,
        enableNext = enableText,
        doneAction = {
            if (selectedIndices.value.isNotEmpty()) {
                moveToNextStep(
                    currentStep = currentStep,
                    contactModel = contactModel,
                    capturedTexts = capturedTexts,
                    selectedIndices = selectedIndices,
                    doneAction = doneAction
                )
            }
        }
    )
}
//endregion

//region BCardContactReviewFormView
@Composable
fun BCardContactReviewFormView(
    currentStep: MutableState<BCardProgressStateModel>,
    capturedTexts: MutableState<List<String>>,
    selectedIndices: MutableState<MutableSet<Int>>
) {

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp)
    ) {
        itemsIndexed(capturedTexts.value) { index, capturedText ->
            val isChecked = remember {
                mutableStateOf(selectedIndices.value.contains(index))
            }
            LaunchedEffect(selectedIndices.value) {
                isChecked.value = selectedIndices.value.contains(index)
            }

            CheckListSectionView(
                text = capturedText,
                isChecked = isChecked,
                currentStep = currentStep,
                selectedIndices = selectedIndices
            ) { value ->
                updateSelection(currentStep, index, value, selectedIndices)
            }
        }
    }
}
//endregion

//region Logics
private fun prepopulateForStep(
    currentStep: MutableState<BCardProgressStateModel>,
    contactModel: MutableState<UpdateContactModel>,
    capturedTexts: MutableState<List<String>>,
    selectedIndices: MutableState<MutableSet<Int>>
) {
    selectedIndices.value = mutableSetOf()
    when (currentStep.value) {
        BCardProgressStateModel.NAME -> {
            val indexes = selectedIndices.value.toMutableSet()
            indexes.addAll(capturedTexts.value.indices.filter { capturedTexts.value[it] == contactModel.value.name })
            selectedIndices.value = indexes
        }

        BCardProgressStateModel.TITLE -> {
            val indexes = selectedIndices.value.toMutableSet()
            indexes.addAll(capturedTexts.value.indices.filter { capturedTexts.value[it] == contactModel.value.title })
            selectedIndices.value = indexes
        }

        BCardProgressStateModel.COMPANY -> {
            val indexes = selectedIndices.value.toMutableSet()
            indexes.addAll(capturedTexts.value.indices.filter { capturedTexts.value[it] == contactModel.value.company })
            selectedIndices.value = indexes
        }

        BCardProgressStateModel.MOBILE -> {
            val indexes = selectedIndices.value.toMutableSet()
            contactModel.value.mobiles?.values?.let { mobiles ->
                indexes.addAll(capturedTexts.value.indices.filter { capturedTexts.value[it] in mobiles })
            }
            selectedIndices.value = indexes
        }

        BCardProgressStateModel.EMAIL -> {
            val indexes = selectedIndices.value.toMutableSet()
            contactModel.value.emails?.values?.let { emails ->
                indexes.addAll(capturedTexts.value.indices.filter { capturedTexts.value[it] in emails })
            }
            selectedIndices.value = indexes
        }

        BCardProgressStateModel.LINKS -> {
            val indexes = selectedIndices.value.toMutableSet()
            contactModel.value.links?.values?.let { links ->
                indexes.addAll(capturedTexts.value.indices.filter { capturedTexts.value[it] in links })
            }
            selectedIndices.value = indexes
        }
    }
}

private fun moveToNextStep(
    currentStep: MutableState<BCardProgressStateModel>,
    contactModel: MutableState<UpdateContactModel>,
    capturedTexts: MutableState<List<String>>,
    selectedIndices: MutableState<MutableSet<Int>>,
    doneAction: ((UpdateContactModel) -> Unit)?
) {
    when (currentStep.value) {
        BCardProgressStateModel.NAME -> {
            contactModel.value.name =
                selectedIndices.value.firstOrNull()?.let {
                    if (it != 0) {
                        capturedTexts.value[it]
                    } else {
                        null
                    }
                }
        }

        BCardProgressStateModel.TITLE -> {
            contactModel.value.title =
                selectedIndices.value.firstOrNull()?.let {
                    if (it != 0) {
                        capturedTexts.value[it]
                    } else {
                        null
                    }
                }
        }

        BCardProgressStateModel.COMPANY -> {
            contactModel.value.company =
                selectedIndices.value.firstOrNull()?.let {
                    if (it != 0) {
                        capturedTexts.value[it]
                    } else {
                        null
                    }
                }
        }

        BCardProgressStateModel.MOBILE -> {
            val mobiles = selectedIndices.value.take(2).mapIndexedNotNull { offset, index ->
                if (index != 0 && capturedTexts.value.getOrNull(index) != null) {
                    val text = capturedTexts.value[index]
                    val filteredText = text.filteringPhoneNumber().trim()
                    "mobile${offset + 1}" to filteredText
                } else {
                    null
                }
            }.toMap()
            contactModel.value.mobiles = mobiles.toMutableMap()
        }

        BCardProgressStateModel.EMAIL -> {
            val emails = selectedIndices.value.take(2).mapIndexedNotNull { offset, index ->
                if (index != 0 && capturedTexts.value.getOrNull(index) != null) {
                    val text = capturedTexts.value[index]
                    val filteredText = text.filteringEmail().trim()
                    "email${offset + 1}" to filteredText
                } else {
                    null
                }
            }.toMap()
            contactModel.value.emails = emails.toMutableMap()
        }

        BCardProgressStateModel.LINKS -> {
            val links = selectedIndices.value.take(2).mapIndexedNotNull { offset, index ->
                if (index != 0 && capturedTexts.value.getOrNull(index) != null) {
                    val text = capturedTexts.value[index]
                    val filteredText = text.filteringLink().trim()
                    "link${offset + 1}" to filteredText
                } else {
                    null
                }
            }.toMap()
            contactModel.value.links = links.toMutableMap()
        }
    }
    if (currentStep.value == BCardProgressStateModel.LINKS) {
        doneAction?.invoke(contactModel.value)
        return
    }
    currentStep.value = currentStep.value.incrementStep()
}

private fun moveToPreviousStep(
    currentStep: MutableState<BCardProgressStateModel>,
    cancelAction: (() -> Unit)?
) {
    if (currentStep.value == BCardProgressStateModel.NAME) {
        cancelAction?.invoke()
    } else {
        currentStep.value = currentStep.value.decrementStep()
    }
}

private fun updateSelection(
    currentStep: MutableState<BCardProgressStateModel>,
    index: Int,
    isChecked: Boolean,
    selectedIndices: MutableState<MutableSet<Int>>
) {
    when (currentStep.value) {
        BCardProgressStateModel.EMAIL, BCardProgressStateModel.MOBILE, BCardProgressStateModel.LINKS -> {
            val indexes = selectedIndices.value.toMutableSet()
            if (!isChecked) {
                indexes.remove(index)
                selectedIndices.value = indexes
                return
            }
            indexes.add(index)
            selectedIndices.value = indexes
        }

        else -> {
            if (isChecked) {
                val indexes = selectedIndices.value.toMutableSet()
                indexes.clear()
                indexes.add(index)
                selectedIndices.value = indexes
            } else {
                val indexes = selectedIndices.value.toMutableSet()
                indexes.remove(index)
                selectedIndices.value = indexes
            }
        }
    }
}
//endregion

//region Preview
@Composable
@Preview
fun BCardContactReviewViewPreview() {
    val capturedTexts = remember {
        mutableStateOf(
            listOf(
                "Not on Card",
                "Akansha Patil",
                "Nectorsource@gmail.com",
                "Nectorsource",
                "google.com"
            )
        )
    }
    val contactModel = remember {
        mutableStateOf(UpdateContactModel.mock())
    }

    BCardContactReviewView(
        contactModel = contactModel,
        capturedTexts = capturedTexts
    )
}
//endregion