package com.icebrekr.icebrekrui.tryouts.leadgeneration

import NavigationAction
import NavigationHeaderView
import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.Looper
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.icebrekr.icebrekrui.configurables.FontType
import com.icebrekr.icebrekrui.configurables.IBCustomFonts
import com.icebrekr.icebrekrui.configurables.IBFonts
import com.icebrekr.icebrekrui.configurables.color.IBTheme
import com.icebrekr.icebrekrui.helper.PackageImage
import com.icebrekr.icebrekrui.moduleviews.leadgeneration.businesscardreader.review.utils.generateImageFileUri
import com.icebrekr.icebrekrui.moduleviews.leadgeneration.businesscardreader.review.utils.startCrop
import com.icebrekr.icebrekrui.uielements.imageview.URLImageView
import com.icebrekr.icebrekrui.uielements.textfield.custom.InfoTextArea
import com.icebrekr.icebrekrui.uielements.textfield.custom.InfoTextField
import com.icebrekr.icebrekrui.uielements.textfield.custom.MobileTextField
import com.icebrekr.sharedresource.sharedmodels.contact.UpdateContactModel
import com.icebrekr.icebrekrui.reusableviews.imagepicker.ImagePicker
import com.icebrekr.icebrekrui.uielements.label.PrimaryLabel
import com.icebrekr.uicomponents.components.buttons.ImageButton
import com.icebrekr.uicomponents.components.progress.ProgressBarView
import com.icebrekr.uicomponents.components.textfield.properties.TextFieldState
import com.yalantis.ucrop.UCrop
import com.icebrekr.icebrekrui.R

sealed class UpdateContactNavigationAction {
    object BackAction : UpdateContactNavigationAction()
    data class primaryAction(val updateContactModel: UpdateContactModel?) : UpdateContactNavigationAction()
    data class secondaryAction(val updateContactModel: UpdateContactModel?) : UpdateContactNavigationAction()
    data class downloadAction(val updateContactModel: UpdateContactModel?) : UpdateContactNavigationAction()
    data class swipeLeftAction(val updateContactModel: UpdateContactModel?) : UpdateContactNavigationAction()
    data class swipeRightAction(val updateContactModel: UpdateContactModel?) : UpdateContactNavigationAction()
}

@Composable
fun UpdateContactView(
    isLoading: MutableState<Boolean>,
    uiContactModel: MutableState<UpdateContactModel>,
    isEditAvailable: Boolean,
    isSwipeAvailable: Boolean,
    onImageSelected: ((Uri) -> Unit),
    openUrlAction: ((String) -> Unit)? = null,
    openEmailAction: ((String) -> Unit)? = null,
    openPhoneAction: ((String) -> Unit)? = null,
    openMessageAction: ((String) -> Unit)? = null,
    action: ((UpdateContactNavigationAction) -> Unit)? = null,
) {
    val context = LocalContext.current
    val showActionSheet = remember {
        mutableStateOf(false)
    }

    val cropImageLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val resultUri = UCrop.getOutput(result.data!!)
                resultUri?.let {
                    onImageSelected(it)
                }
            }
        }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri ->
            uri?.let {
                startCrop(context, it, cropImageLauncher)
            }
        }
    )

    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { result ->
            if (result) {
                startCrop(context, generateImageFileUri(context = context), cropImageLauncher)
            }
        }

    ImagePicker(
        showActionSheet = showActionSheet,
        onCameraOptionSelected = {
            try {
                cameraLauncher.launch(generateImageFileUri(context = context))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        },
        onGalleryOptionSelected = {
            try {
                galleryLauncher.launch(arrayOf("image/*"))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    ) {
        Surface(
            color = IBTheme.theme.white,
            modifier = Modifier.fillMaxSize()
        ) {
            Column {
                NavigationHeaderView(
                    titleText = "Contact",
                    backActionIcon = PackageImage.closeBlue.toImage(),
                    primaryActionIcon = PackageImage.export.toImage(),
                    secondaryActionIcon = PackageImage.checkMarkBlue.toImage(),
                    action = {
                        when(it){
                            NavigationAction.backAction -> {
                                action?.invoke(UpdateContactNavigationAction.BackAction)
                            }
                            NavigationAction.primaryAction -> {
                                action?.invoke(UpdateContactNavigationAction.primaryAction(uiContactModel.value))
                            }
                            NavigationAction.secondaryAction -> {
                                action?.invoke(UpdateContactNavigationAction.secondaryAction(uiContactModel.value))
                            }
                        }
                    }
                )
                DownloadView(
                    action,
                    uiContactModel
                )
                Box(

                ) {
                    FormView(
                        context,
                        uiContactModel,
                        isEditAvailable,
                        isSwipeAvailable,
                        showActionSheet,
                        openUrlAction,
                        openEmailAction,
                        openPhoneAction,
                        openMessageAction,
                        action
                    )
                    if (isLoading.value) {
                        ProgressBarView()
                    }
                }
            }
        }
    }
}

@Composable
fun DownloadView(
    action: ((UpdateContactNavigationAction) -> Unit)?,
    uiContactModel: MutableState<UpdateContactModel>
) {
    Box(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.CenterEnd)
                .clickable {
                    action?.invoke(UpdateContactNavigationAction.downloadAction(uiContactModel.value))
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            PrimaryLabel(
                text = "Download",
                font = IBFonts.appliedFont(IBCustomFonts.bold, FontType.body),
                foregroundColor = IBTheme.theme.danger
            )
            Spacer(modifier = Modifier.width(8.dp))
            ImageButton(
                image = PackageImage.download.toImage(),
                foregroundColor = IBTheme.theme.danger
            ) {
                action?.invoke(UpdateContactNavigationAction.downloadAction(uiContactModel.value))
            }
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}

@Composable
fun FormView(
    context: Context,
    uiContactModel1: MutableState<UpdateContactModel>,
    isEditAvailable: Boolean,
    isSwipeAvailable: Boolean,
    showActionSheet: MutableState<Boolean>,
    openUrlAction: ((String) -> Unit)?,
    openEmailAction: ((String) -> Unit)?,
    openPhoneAction: ((String) -> Unit)?,
    openMessageAction: ((String) -> Unit)?,
    action: ((UpdateContactNavigationAction) -> Unit)?
) {

    val uiContactModel = remember {
        uiContactModel1
    }

    var showDialog = remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted: Boolean ->
            if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) || isGranted) {
                if (showActionSheet.value) {
                    showActionSheet.value = false
                    Handler(Looper.getMainLooper()).postDelayed(
                        {
                            showActionSheet.value = true
                        }, 200
                    )
                } else {
                    showActionSheet.value = true
                }
            } else {
                showDialog.value = true
            }
        }
    )

    LazyColumn(
        modifier = Modifier.padding(16.dp)
    ) {
        item {
            InfoTextField(
                text = uiContactModel.value.name ?: "",
                placeHolder = "Enter the Name",
                title = "Name",
                allowsEmptyInput = false,
                primaryActionImage = null,
                secondaryActionImage = null,
                primaryButtonAction = null,
                secondaryButtonAction = null,
                textChangeHandler = {
                    uiContactModel.value.name = if (!it.trim().isNullOrEmpty()) it.trim() else null
                }
            )
        }

        item {
            if (isSwipeAvailable) {
                ArrayView(
                    action,
                    uiContactModel
                )
            }
        }

        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .wrapContentSize()
                ) {
                    IconButton(onClick = {
                        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) || ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.READ_EXTERNAL_STORAGE
                            ) == PackageManager.PERMISSION_GRANTED
                        ) {
                            if (showActionSheet.value) {
                                showActionSheet.value = false
                                Handler(Looper.getMainLooper()).postDelayed(
                                    {
                                        showActionSheet.value = true
                                    }, 200
                                )
                            } else {
                                showActionSheet.value = true
                            }
                        } else {
                            permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                        }
                    }) {

                        if (uiContactModel.value.photo.isNullOrEmpty()) {
                            Image(
                                painter = painterResource(id = PackageImage.addPhoto.toImage()),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .aspectRatio(1f)
                                    .clip(RoundedCornerShape(10.dp)),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            URLImageView(
                                urlString = uiContactModel.value.photo,
                                placeholderImage = painterResource(id = PackageImage.addPhoto.toImage())
                            )
                        }

                    }

                }
            }
        }

        item {
            MobileTextField(
                text = uiContactModel.value.mobiles?.get("mobile1") ?: "",
                placeHolder = "Enter the Primary Contact Number",
                title = "Contact - Phone",
                textFieldState = if (isEditAvailable || uiContactModel.value.mobiles?.get("mobile1").isNullOrEmpty()) TextFieldState.DEFAULT else TextFieldState.DISABLED,
                primaryActionImage = PackageImage.phone.toImage(),
                secondaryActionImage = PackageImage.message.toImage(),
                primaryButtonAction = {
                    uiContactModel.value.mobiles?.get("mobile1")
                        ?.let { it1 -> openPhoneAction?.invoke(it1) }
                },
                secondaryButtonAction = {
                    uiContactModel.value.mobiles?.get("mobile1")
                        ?.let { it1 -> openMessageAction?.invoke(it1) }
                },
                textChangeHandler = {
                    if (!it.trim().isNullOrEmpty()) {
                        if (uiContactModel.value.mobiles.isNullOrEmpty()) {
                            uiContactModel.value.mobiles = mutableMapOf()
                        }
                        uiContactModel.value.mobiles?.put(
                            "mobile1",
                            it.trim()
                        )
                        uiContactModel.value.primaryMobile = it.trim()
                    } else {
                        uiContactModel.value.mobiles?.remove("mobile1")
                        uiContactModel.value.primaryMobile = null
                    }
                }
            )
        }
        item {
            MobileTextField(
                text = uiContactModel.value.mobiles?.get("mobile2") ?: "",
                placeHolder = "Enter the Secondary Contact Number",
                title = "Contact - Work",
                primaryActionImage = PackageImage.phone.toImage(),
                secondaryActionImage = PackageImage.message.toImage(),
                primaryButtonAction = {
                    uiContactModel.value.mobiles?.get("mobile2")
                        ?.let { it1 -> openPhoneAction?.invoke(it1) }
                },
                secondaryButtonAction = {
                    uiContactModel.value.mobiles?.get("mobile2")
                        ?.let { it1 -> openMessageAction?.invoke(it1) }
                },
                textChangeHandler = {
                    if (!it.trim().isNullOrEmpty()) {
                        if (uiContactModel.value.mobiles.isNullOrEmpty()) {
                            uiContactModel.value.mobiles = mutableMapOf()
                        }
                        uiContactModel.value.mobiles?.put(
                            "mobile2",
                            it.trim()
                        )
                    } else uiContactModel.value.mobiles?.remove("mobile2")
                }
            )
        }
        item {
            InfoTextField(
                text = uiContactModel.value.emails?.get("email1") ?: "",
                placeHolder = "Enter the Primary Email ID",
                title = "Email",
                textFieldState = if (isEditAvailable || uiContactModel.value.emails?.get("email1").isNullOrEmpty()) TextFieldState.DEFAULT else TextFieldState.DISABLED,
                primaryActionImage = PackageImage.mail.toImage(),
                secondaryActionImage = null,
                primaryButtonAction = {
                    uiContactModel.value.primaryEmail?.let { it1 ->
                        openEmailAction?.invoke(
                            it1
                        )
                    }
                },
                secondaryButtonAction = null,
                textChangeHandler = {
                    if (!it.trim().isNullOrEmpty()) {
                        if (uiContactModel.value.emails.isNullOrEmpty()) {
                            uiContactModel.value.emails = mutableMapOf()
                        }
                        uiContactModel.value.emails?.put(
                            "email1",
                            it.trim()
                        )
                        uiContactModel.value.primaryEmail = it.trim()
                    } else {
                        uiContactModel.value.emails?.remove("email1")
                        uiContactModel.value.primaryEmail = null
                    }
                }
            )
        }
        item {
            InfoTextField(
                text = uiContactModel.value.emails?.get("email2") ?: "",
                placeHolder = "Enter the Secondary Email ID",
                title = "Email - Work",
                primaryActionImage = PackageImage.mail.toImage(),
                secondaryActionImage = null,
                primaryButtonAction = {
                    uiContactModel.value.emails?.get("email2")
                        ?.let { it1 -> openEmailAction?.invoke(it1) }
                },
                secondaryButtonAction = null,
                textChangeHandler = {
                    if (!it.trim().isNullOrEmpty()) {
                        if (uiContactModel.value.emails.isNullOrEmpty()) {
                            uiContactModel.value.emails = mutableMapOf()
                        }
                        uiContactModel.value.emails?.put(
                            "email2",
                            it.trim()
                        )
                    } else uiContactModel.value.emails?.remove("email2")
                }
            )
        }
        item {
            InfoTextField(
                text = uiContactModel.value.title ?: "",
                placeHolder = "Enter the Current Role",
                title = "Current Role",
                primaryActionImage = null,

                primaryButtonAction = null,
                secondaryButtonAction = null,
                textChangeHandler = {
                    uiContactModel.value.title = if (!it.trim().isNullOrEmpty()) it.trim() else null
                }
            )
        }
        item {
            InfoTextField(
                text = uiContactModel.value.company ?: "",
                placeHolder = "Enter the Company Name",
                title = "Company Name",
                primaryActionImage = null,

                primaryButtonAction = null,
                secondaryButtonAction = null,
                textChangeHandler = {
                    uiContactModel.value.company =
                        if (!it.trim().isNullOrEmpty()) it.trim() else null
                }
            )
        }

        item {
            InfoTextArea(
                text = uiContactModel.value.note ?: "",
                placeHolder = "Enter the Notes",
                title = "Notes",
                textChangeHandler = {
                    uiContactModel.value.note = if (!it.trim().isNullOrEmpty()) it.trim() else null
                }
            )
        }
        item {
            InfoTextField(
                text = uiContactModel.value.links?.get("link1") ?: "",
                placeHolder = "Full link address",
                title = "Link 1",
                primaryActionImage = PackageImage.link.toImage(),
                secondaryActionImage = null,
                primaryButtonAction = { openUrlAction?.invoke(uiContactModel.value.links?.get("link1") ?: "") },
                secondaryButtonAction = null,
                textChangeHandler = {
                    if (it.trim().isNotEmpty()) {
                        if (uiContactModel.value.links.isNullOrEmpty()) {
                            uiContactModel.value.links = mutableMapOf()
                        }
                        uiContactModel.value.links?.put(
                            "link1",
                            it.trim()
                        )
                    } else uiContactModel.value.links?.remove("link1")
                }
            )
        }
        item {
            InfoTextField(
                text = uiContactModel.value.links?.get("link2") ?: "",
                placeHolder = "Full link address",
                title = "Link 2",
                primaryActionImage = PackageImage.link.toImage(),
                secondaryActionImage = null,
                primaryButtonAction = { openUrlAction?.invoke(uiContactModel.value.links?.get("link2") ?: "") },
                secondaryButtonAction = null,
                textChangeHandler = {
                    if (it.trim().isNotEmpty()) {
                        if (uiContactModel.value.links.isNullOrEmpty()) {
                            uiContactModel.value.links = mutableMapOf()
                        }
                        uiContactModel.value.links?.put(
                            "link2",
                            it.trim()
                        )
                    } else uiContactModel.value.links?.remove("link2")
                }
            )
        }
        item {
            InfoTextField(
                text = uiContactModel.value.links?.get("link3") ?: "",
                placeHolder = "Full link address",
                title = "Link 3",
                primaryActionImage = PackageImage.link.toImage(),
                secondaryActionImage = null,
                primaryButtonAction = { openUrlAction?.invoke(uiContactModel.value.links?.get("link3") ?: "") },
                secondaryButtonAction = null,
                textChangeHandler = {
                    if (it.trim().isNotEmpty()) {
                        if (uiContactModel.value.links.isNullOrEmpty()) {
                            uiContactModel.value.links = mutableMapOf()
                        }
                        uiContactModel.value.links?.put(
                            "link3",
                            it.trim()
                        )
                    } else uiContactModel.value.links?.remove("link3")
                }
            )
        }
    }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text("Permission Denied") },
            text = { Text("Please provide permission to access the photos.") },
            confirmButton = {
                Button(onClick = {
                    showDialog.value = false
                }) {
                    Text("OK")

                }
            }
        )
    }

}

@Composable
fun ArrayView(
    action: ((UpdateContactNavigationAction) -> Unit)?,
    uiContactModel: MutableState<UpdateContactModel>
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_close_blue),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clickable {
                    action?.invoke(UpdateContactNavigationAction.swipeLeftAction(uiContactModel.value))
                }
        )

        Spacer(modifier = Modifier.weight(1f))

        Image(
            painter = painterResource(id = R.drawable.ic_close_blue),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clickable {
                    action?.invoke(UpdateContactNavigationAction.swipeRightAction(uiContactModel.value))
                }
        )
    }
}

@Preview
@Composable
fun UpdateContactViewPreviews() {
    val isLoading = remember {
        mutableStateOf(false)
    }
    val uiContactModel = remember {
        mutableStateOf(
            UpdateContactModel(
                id = "id",
                name = "contactModel.lastName",
                primaryMobile = "contactModel.primaryMobile",
                primaryEmail = "contactModel.primaryEmail",
                title = "contactModel.title",
                company = "contactModel.company",
                note = "contactModel.note",
                links = null,
                emails = null,
                mobiles = null,
                photo = null,
                lastUpdatedAt = null
            )
        )
    }
    UpdateContactView(
        isLoading,
        isEditAvailable = false,
        isSwipeAvailable = true,
        uiContactModel = uiContactModel,
        onImageSelected = {
            print(it)
        })
}