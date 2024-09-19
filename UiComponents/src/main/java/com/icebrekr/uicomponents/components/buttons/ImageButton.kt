package com.icebrekr.uicomponents.components.buttons

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icebrekr.uicomponents.R

//region Image Button
@Composable
fun ImageButton(
    image: Int,
    foregroundColor: Color = Color.Black,
    dimension: Pair<Float, Float> = Pair(24f,24f),
    onTap: () -> Unit
) {
    IconButton(
        onClick = onTap,
        modifier = Modifier.size(dimension.first.dp, dimension.second.dp)
    ) {
        Icon(
            painter = painterResource(image),
            contentDescription = null,
            tint = foregroundColor,
            modifier = Modifier
                .size(dimension.first.dp, dimension.second.dp)
                .aspectRatio(
                    dimension.first / dimension.second
                )
        )
    }
}
//endregion

//region Image Button Preview
@Preview
@Composable
fun ImageButtonPreview() {
    ImageButton(
        image = R.drawable.ic_back_arrow,
        foregroundColor = Color.Red,
        dimension = Pair(30f, 30f),
        onTap = {
            // Action to perform on click
            println("ImageButton clicked")
        }
    )
}
//endregion