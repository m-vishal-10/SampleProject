package com.icebrekr.icebrekrui.uielements.imageview

import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.icebrekr.icebrekrui.R

@Composable
fun URLImageView(
    urlString: String?,
    placeholderImage: Painter = painterResource(id = R.drawable.ic_person),
    loaderImage: Painter = painterResource(id = R.drawable.ic_placeholder)
) {

    if (!urlString.isNullOrEmpty() && urlString.startsWith("http")) {

        if (!urlString.endsWith(".gif", ignoreCase = true)) {

            val imageLoader = ImageLoader.Builder(LocalContext.current)
                .diskCachePolicy(CachePolicy.ENABLED)
                .memoryCachePolicy(CachePolicy.ENABLED)
                .respectCacheHeaders(false)
                .build()

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(urlString)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .aspectRatio(1f, false)
                    .clip(RoundedCornerShape(10.dp)),
                placeholder = loaderImage,
                fallback = placeholderImage,
                error = placeholderImage,
                imageLoader = imageLoader
            )

        } else {
            Image(
                painter = placeholderImage,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )
        }

    } else if (!urlString.isNullOrEmpty()) {
        val base64ImageBitmap = try {
            val imageBytes = Base64.decode(urlString, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        } catch (e: Exception) {
            null
        }
        if (base64ImageBitmap != null) {
            Image(
                bitmap = base64ImageBitmap.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .sizeIn(
                        maxWidth = 300.dp,
                        maxHeight = 300.dp
                    )
            )
        } else {
            Image(
                painter = placeholderImage,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )
        }
    } else {
        Image(
            painter = placeholderImage,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(10.dp)),
            contentScale = ContentScale.Crop
        )
    }
}

@Preview
@Composable
fun URLImageViewPreview() {
    URLImageView(
        urlString = "https://example.com/image.jpg",
        placeholderImage = painterResource(id = R.drawable.ic_person),
        loaderImage = painterResource(id = R.drawable.ic_placeholder)
    )
}

