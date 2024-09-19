package com.icebrekr.icebrekrui.moduleviews.leadgeneration.businesscardreader.review.views

import NavigationHeaderView
import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.icebrekr.icebrekrui.configurables.color.IBTheme
import com.icebrekr.icebrekrui.helper.PackageImage
import com.icebrekr.icebrekrui.moduleviews.leadgeneration.businesscardreader.review.components.BCardReviewInfoView
import com.icebrekr.icebrekrui.uielements.textfield.custom.InfoTextArea
import com.icebrekr.icebrekrui.uielements.textfield.custom.InfoTextField
import com.icebrekr.icebrekrui.uielements.textfield.custom.MobileTextField
import com.icebrekr.sharedresource.sharedmodels.contact.UpdateContactModel

//region BCard Contact Matches View
@Composable
fun BCardContactMatchesView(
    contactModel: UpdateContactModel,
    croppedImage: Bitmap?,
    backAction: (() -> Unit)?,
    yesAction: ((UpdateContactModel) -> Unit)?,
    noAction: ((UpdateContactModel) -> Unit)?
) {
    Surface(
        color = IBTheme.theme.white,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            HeaderView(backAction)
            MatchesView(yesAction, noAction, contactModel)
            FormView(contactModel, croppedImage)
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}
//endregion

//region Header View
@Composable
private fun HeaderView(backAction: (() -> Unit)?) {
    NavigationHeaderView(
        titleText = "Contact Review",
        backActionIcon = PackageImage.backChevron.toImage(),
        primaryActionIcon = null,
        secondaryActionIcon = null,
        action = {
            backAction?.invoke()
        }
    )
}
//endregion

//region MatchesView
@Composable
private fun MatchesView(
    yesAction: ((UpdateContactModel) -> Unit)?,
    noAction: ((UpdateContactModel) -> Unit)?,
    updateContactModel: UpdateContactModel
) {
    BCardReviewInfoView(
        yesAction = { yesAction?.invoke(updateContactModel) },
        noAction = { noAction?.invoke(updateContactModel) }
    )
}
//endregion

//region FormView
@Composable
private fun FormView(contactModel: UpdateContactModel, croppedImage: Bitmap?) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally // Center items horizontally
    ) {
        croppedImage?.let {
            item {
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .sizeIn(
                            maxWidth = 300.dp,
                            maxHeight = 300.dp
                        )
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = rememberImagePainter(it),
                        contentDescription = null,
                        modifier = Modifier
                            .wrapContentSize()
                            .sizeIn(
                                maxWidth = 300.dp,
                                maxHeight = 300.dp
                            ),
                    )
                }
            }
        }

        item {
            InfoTextField(
                text = contactModel.name ?: "",
                placeHolder = "Enter the Name",
                title = "Name",
                allowsEmptyInput = false,
                textChangeHandler = { text ->
                    contactModel.name = if (text.trim().isEmpty()) null else text
                })
        }

        item {
            MobileTextField(
                text = contactModel.mobiles?.get("mobile1") ?: "",
                placeHolder = "Enter the Primary Contact Number",
                title = "Contact - Phone",
                textChangeHandler = { text ->
                    if (text.trim().isNotEmpty()) contactModel.mobiles?.put("mobile1", text)
                    else contactModel.mobiles?.remove("mobile1")
                }
            )
        }

        item {
            MobileTextField(
                text = contactModel.mobiles?.get("mobile2") ?: "",
                placeHolder = "Enter the Secondary Contact Number",
                title = "Contact - Work",
                textChangeHandler = { text ->
                    if (text.trim().isNotEmpty()) contactModel.mobiles?.put("mobile2", text)
                    else contactModel.mobiles?.remove("mobile2")
                }
            )
        }

        item {
            InfoTextField(
                text = contactModel.emails?.get("email1") ?: "",
                placeHolder = "Enter the Primary Email ID",
                title = "Email",
                textChangeHandler = { text ->
                    if (text.trim().isNotEmpty()) contactModel.emails?.put("email1", text)
                    else contactModel.emails?.remove("email1")
                }
            )
        }

        item {
            InfoTextField(
                text = contactModel.emails?.get("email2") ?: "",
                placeHolder = "Enter the Secondary Email ID",
                title = "Email - Work",
                textChangeHandler = { text ->
                    if (text.trim().isNotEmpty()) contactModel.emails?.put("email2", text)
                    else contactModel.emails?.remove("email2")
                }
            )
        }

        item {
            InfoTextField(
                text = contactModel.title ?: "",
                placeHolder = "Enter the Current Role",
                title = "Current Role",
                textChangeHandler = { text ->
                    contactModel.title = if (text.trim().isEmpty()) null else text
                }
            )
        }

        item {
            InfoTextField(
                text = contactModel.company ?: "",
                placeHolder = "Enter the Company Name",
                title = "Company Name",
                textChangeHandler = { text ->
                    contactModel.company = if (text.trim().isEmpty()) null else text
                }
            )
        }

        item {
            InfoTextArea(
                text = contactModel.note ?: "",
                placeHolder = "Enter the Notes",
                title = "Notes",
                textChangeHandler = { text ->
                    contactModel.note = if (text.trim().isEmpty()) null else text
                }
            )
        }

        item {
            InfoTextField(
                text = contactModel.links?.get("link1") ?: "",
                placeHolder = "Full link address",
                title = "Link 1",
                textChangeHandler = { text ->
                    if (text.trim().isNotEmpty()) {
                        contactModel.links?.put("link1", text.trim())
                    }
                    else {
                        contactModel.links?.remove("link1")
                    }
                }
            )
        }

        item {
            InfoTextField(
                text = contactModel.links?.get("link2") ?: "",
                placeHolder = "Full link address",
                title = "Link 2",
                textChangeHandler = { text ->
                    if (text.trim().isNotEmpty()) {
                        contactModel.links?.put("link2", text.trim())
                    }
                    else contactModel.links?.remove("link2")
                }
            )
        }

        item {
            InfoTextField(
                text = contactModel.links?.get("link3") ?: "",
                placeHolder = "Full link address",
                title = "Link 3",
                textChangeHandler = { text ->
                    if (text.trim().isNotEmpty()) {
                        contactModel.links?.put("link3", text.trim())
                    }
                    else contactModel.links?.remove("link3")
                }
            )
        }
    }
}
//endregion

//region Preview
@Preview
@Composable
fun BCardContactMatchesViewPreview() {
    BCardContactMatchesView(
        contactModel = UpdateContactModel.mock(),
        croppedImage = null,
        backAction = {},
        yesAction = {},
        noAction = {}
    )
}
//endregion