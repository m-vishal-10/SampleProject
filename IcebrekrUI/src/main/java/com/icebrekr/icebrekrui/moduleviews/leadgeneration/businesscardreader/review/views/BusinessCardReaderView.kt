package com.icebrekr.icebrekrui.moduleviews.leadgeneration.businesscardreader.review.views

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraControl
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.icebrekr.icebrekrui.configurables.color.IBTheme
import com.icebrekr.icebrekrui.helper.PackageImage
import com.icebrekr.icebrekrui.moduleviews.leadgeneration.businesscardreader.review.utils.rotateBitmapIfRequired
import com.icebrekr.icebrekrui.moduleviews.leadgeneration.businesscardreader.review.utils.startCrop
import com.icebrekr.icebrekrui.reusableviews.qrcode.CameraScanViewContent
import com.icebrekr.icebrekrui.uielements.toggle.CustomToggleView
import com.icebrekr.uicomponents.components.buttons.ImageButton
import com.yalantis.ucrop.UCrop
import kotlinx.coroutines.delay
import java.io.File

//region BusinessCardReaderView
@Composable
fun BusinessCardReaderView(
    isAutoFocusEnabled: MutableState<Boolean?>,
    hasCameraPermission: MutableState<Boolean>,
    imageCompletionAction: (Bitmap) -> Unit,
    isCameraFlashOn: MutableState<Boolean>,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalContext.current as androidx.lifecycle.LifecycleOwner
    val imageCapture = remember { mutableStateOf<ImageCapture?>(null) }
    val cameraExecutor = ContextCompat.getMainExecutor(context)
    val cameraControl = remember { mutableStateOf<CameraControl?>(null) }
    val isButtonEnabled = remember { mutableStateOf(true) }  // State to manage button enabled/disabled

    val cropImageLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val resultUri = UCrop.getOutput(result.data!!)
                resultUri?.let {
                    val bitmap =
                        BitmapFactory.decodeStream(context.contentResolver.openInputStream(it))
                    imageCompletionAction(bitmap)
                }
            }
        }

    if (hasCameraPermission.value) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .height(500.dp) // Set the height to 500.dp
                    .fillMaxWidth() // Ensure it fills the width of its parent
            ) {
                AndroidView(factory = { ctx ->
                    val previewView = PreviewView(ctx)
                    val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)
                    cameraProviderFuture.addListener({
                        try {
                            val cameraProvider = cameraProviderFuture.get()
                            val previewBuilder = Preview.Builder()
                            val preview = previewBuilder.build().apply {
                                setSurfaceProvider(previewView.surfaceProvider)
                            }
                            imageCapture.value = ImageCapture.Builder()
                                .setTargetRotation(previewView.display.rotation)
                                .build()
                            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                            cameraProvider.unbindAll()
                            val camera = cameraProvider.bindToLifecycle(
                                lifecycleOwner,
                                cameraSelector,
                                preview,
                                imageCapture.value
                            )
                            cameraControl.value =
                                camera.cameraControl // Update the camera control reference
                        } catch (e: Exception) {
                            println("Camera binding failed")
                        }
                    }, cameraExecutor)
                    previewView
                },
                    modifier = Modifier.height(500.dp) // Set the height to 500.dp
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CustomToggleView(
                    labelText = "AutoFocus",
                    isOn = isAutoFocusEnabled
                )
                Spacer(
                    modifier = Modifier.height(8.dp)
                )
                ImageButton(
                    image = PackageImage.capture.toImage(),
                    dimension = Pair(48f, 48f),
                    foregroundColor = IBTheme.theme.white
                ) {
                    if (isButtonEnabled.value) {
                        isButtonEnabled.value = false
                        val photoFile = File(
                            context.filesDir,
                            "temp_image.jpg"
                        )

                        val outputOptions =
                            ImageCapture.OutputFileOptions.Builder(photoFile).build()

                        imageCapture.value?.takePicture(
                            outputOptions,
                            cameraExecutor,
                            object : ImageCapture.OnImageSavedCallback {
                                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                                    val savedUri: Uri? = Uri.fromFile(photoFile)
                                    savedUri?.let {
                                        if (isAutoFocusEnabled.value != false) {
                                            val bitmap =
                                                BitmapFactory.decodeStream(
                                                    context.contentResolver.openInputStream(
                                                        it
                                                    )
                                                )
                                            val rotatedBitmap =
                                                rotateBitmapIfRequired(bitmap, it)
                                            imageCompletionAction.invoke(rotatedBitmap)
                                        } else {
                                            startCrop(context, savedUri, cropImageLauncher)
                                        }
                                    }
                                }

                                override fun onError(exception: ImageCaptureException) {
                                    println("Camera binding failed")
                                }
                            })
                    }
                }
            }
        }

        // Listen for changes in isCameraFlashOn and update the torch state accordingly
        LaunchedEffect(isCameraFlashOn.value) {
            cameraControl.value?.enableTorch(isCameraFlashOn.value)
        }

        LaunchedEffect(isButtonEnabled.value) {
            if (!isButtonEnabled.value) {
                delay(2000)
                isButtonEnabled.value = true
            }
        }

    } else {
        CameraScanViewContent()
    }

}
//endregion