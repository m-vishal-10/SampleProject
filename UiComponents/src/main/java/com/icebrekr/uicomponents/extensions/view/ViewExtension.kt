package com.icebrekr.uicomponents.extensions.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

//region Extension function to add a bordered modifier
fun Modifier.bordered(
    cornerRadius: Dp,
    borderWidth: Dp,
    borderColor: Color
): Modifier = this
    .clip(RoundedCornerShape(cornerRadius))
    .then(
        Modifier
            .background(borderColor, shape = RoundedCornerShape(cornerRadius))
            .padding(borderWidth)
    )
//endregion