package com.icebrekr.icebrekrui.uielements.toggle

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.icebrekr.icebrekrui.uielements.label.PrimaryLabel

//region Custom Toggle
@Composable
fun CustomToggleView(
    labelText: String,
    isOn: MutableState<Boolean?>,
    toggleAction: ((Boolean) -> Unit)? = null
) {
    Column {
        Row(
            modifier = Modifier
                .border(
                    width = 2.dp,
                    color = IBTheme.theme.content2,
                    shape = RoundedCornerShape(30.dp)
                )
                .background(IBTheme.theme.grey1, RoundedCornerShape(30.dp))
                .padding(horizontal = 16.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            PrimaryLabel(
                text = labelText,
                font = IBFonts.appliedFont(customFont = IBCustomFonts.bold, type = FontType.callout),
                foregroundColor = IBTheme.theme.content2
            )
            CustomToggleStyle(isOn = isOn, toggleAction = toggleAction)
        }
    }
}
//endregion

//region Custom Toggle Style
@Composable
fun CustomToggleStyle(
    isOn: MutableState<Boolean?>,
    toggleAction: ((Boolean) -> Unit)? = null
) {
    Box(
        modifier = Modifier
            .width(60.dp)
            .padding(8.dp) // Padding around the toggle
            .clickable {
                if (isOn.value == null) {
                    isOn.value = true
                } else {
                    isOn.value = !isOn.value!!
                }
                toggleAction?.invoke(isOn.value!!)
            },
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .width(50.dp)
                .height(25.dp)
                .background(
                    color = if (isOn.value != false) IBTheme.theme.primary1Default else IBTheme.theme.content2,
                    shape = RoundedCornerShape(20.dp)
                )
        )
        Box(
            modifier = Modifier
                .size(20.dp)
                .padding(2.dp) // Padding inside the toggle
                .offset(x = if (isOn.value != false) 12.5.dp else (-12.5).dp)
                .background(
                    color = if (isOn.value != false) IBTheme.theme.content2 else IBTheme.theme.primary1Default,
                    shape = CircleShape
                )
                .animateContentSize(animationSpec = tween(durationMillis = 200))
        )
    }
}
//endregion


//region Preview
@Preview
@Composable
fun CustomTogglePreview() {
    val toggleState = remember { mutableStateOf<Boolean?>(false) }
    CustomToggleView(
        labelText = "Auto Focus",
        isOn = toggleState,
        toggleAction = null
    )
}
//endregion