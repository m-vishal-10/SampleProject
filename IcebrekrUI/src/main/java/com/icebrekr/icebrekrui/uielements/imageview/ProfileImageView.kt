package com.icebrekr.icebrekrui.uielements.imageview

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.icebrekr.icebrekrui.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProfileImageView(
    userId: String,
    isChecked: Boolean,
    link: String?,
    onTap: ((String) -> Unit)? = null,
    onLongPress: ((String) -> Unit)? = null
) {
    Box(
        modifier = Modifier
            .size(56.dp)
            .padding(2.dp)
            .combinedClickable(
                onClick = { onTap?.invoke(userId) },
                onLongClick = { onLongPress?.invoke(userId) }
            )
    ) {
        Card(
            modifier = Modifier.size(56.dp),
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(width = 1.dp, color = Color.Black)
        ) {
            URLImageView(
                urlString = link
            )
        }
        if (isChecked) {
            Image(
                painter = painterResource(id = R.drawable.ic_check_mark),
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
                    .align(Alignment.TopEnd)
                    .offset(x = (10).dp, y = (-5).dp),
            contentScale = ContentScale.FillBounds
            )
        }
    }
}


@Preview
@Composable
fun ProfileImageViewPreview() {
    ProfileImageView(
        userId = "id",
        isChecked = true,
        link = "https://fastly.picsum.photos/id/13/2500/1667.jpg?hmac=SoX9UoHhN8HyklRA4A3vcCWJMVtiBXUg0W4ljWTor7s"
    )
}
