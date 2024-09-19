package com.icebrekr.icebrekrui.moduleviews.leadgeneration.businesscardreader.review.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.core.content.FileProvider
import androidx.exifinterface.media.ExifInterface
import com.yalantis.ucrop.UCrop
import java.io.File

//region Image Cropping
fun startCrop(
    context: Context,
    uri: Uri,
    cropImageLauncher: ManagedActivityResultLauncher<Intent, ActivityResult>
) {
    val destinationUri = Uri.fromFile(File(context.cacheDir, "cropped_image.jpg"))
    val options = UCrop.Options().apply {
        setFreeStyleCropEnabled(true)
        setHideBottomControls(true)
        setCompressionQuality(100)
    }
    val intent = UCrop.of(uri, destinationUri)
        .withOptions(options)
        .withAspectRatio(1f, 1f)
        .withMaxResultSize(1080, 1080)
        .getIntent(context)
    cropImageLauncher.launch(intent)
}

fun rotateBitmapIfRequired(bitmap: Bitmap, uri: Uri): Bitmap {
    val inputStream = uri.path?.let { File(it).inputStream() }
    val exif = inputStream?.let { ExifInterface(it) }

    return when (exif?.getAttributeInt(
        ExifInterface.TAG_ORIENTATION,
        ExifInterface.ORIENTATION_NORMAL
    )) {
        ExifInterface.ORIENTATION_ROTATE_90 -> rotateBitmap(bitmap, 90)
        ExifInterface.ORIENTATION_ROTATE_180 -> rotateBitmap(bitmap, 180)
        ExifInterface.ORIENTATION_ROTATE_270 -> rotateBitmap(bitmap, 270)
        else -> bitmap
    }
}

fun rotateBitmap(bitmap: Bitmap, degrees: Int): Bitmap {
    val matrix = Matrix()
    matrix.postRotate(degrees.toFloat())
    return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
}

fun generateImageFileUri(context: Context): Uri {
    val file = File(context.filesDir, "temp_image.jpg")
    return FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
}
//endregion