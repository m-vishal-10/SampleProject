package com.icebrekr.icebrekrui.uitryouts.eventdiscussion

import com.icebrekr.icebrekrui.R
import NavigationAction
import androidx.compose.foundation.layout.Column
import com.icebrekr.uicomponents.components.buttons.ImageButton
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icebrekr.icebrekrui.configurables.FontType
import com.icebrekr.icebrekrui.configurables.IBCustomFonts
import com.icebrekr.icebrekrui.configurables.IBFonts
import com.icebrekr.icebrekrui.configurables.color.IBTheme
import com.icebrekr.icebrekrui.uielements.label.PrimaryLabel

//region Navigation Header View
@Composable
fun EventDiscussionHeaderView(
    titleText: String,
    closeIcon: Int = com.icebrekr.icebrekrui.R.drawable.ic_close_blue,
    primaryActionIcon: Int? = null,
    secondaryActionIcon: Int? = null,
    action: ((NavigationAction) -> Unit)? = null
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        // Back Action Button
        ImageButton(image = closeIcon, foregroundColor = IBTheme.theme.primary1Default) {
            action?.invoke(NavigationAction.backAction)
        }

        if (primaryActionIcon != null && secondaryActionIcon != null) {
            Spacer(modifier = Modifier.weight(1.5f))
        } else if (primaryActionIcon != null || secondaryActionIcon != null) {
            Spacer(modifier = Modifier.weight(1.0f))
        } else {
            Spacer(modifier = Modifier.weight(0.75f))
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            PrimaryLabel(
                text = titleText,
                font = IBFonts.appliedFont(IBCustomFonts.bold, FontType.title1),
                foregroundColor = IBTheme.theme.primary1Default
            )
            Spacer(modifier = Modifier.height(0.1.dp))
            PrimaryLabel(
                text = "Austin Person of Interest",
                font = IBFonts.appliedFont(IBCustomFonts.bold, FontType.caption1),
                foregroundColor = IBTheme.theme.primary2Disabled
            )

        }


        Spacer(modifier = Modifier.weight(1f))

        primaryActionIcon?.let {
            ImageButton(image = it, foregroundColor = IBTheme.theme.primary1Default) {
                action?.invoke(NavigationAction.primaryAction)
            }
            Spacer(Modifier.width(10.dp))
        }

        secondaryActionIcon?.let {
            ImageButton(image = it, foregroundColor = IBTheme.theme.primary1Default) {
                action?.invoke(NavigationAction.secondaryAction)
            }
            Spacer(Modifier.width(10.dp))
        }

    }


}
//endregion

//region - Preview
@Preview(showBackground = true)
@Composable
fun EventDiscussionHeaderViewPreview() {
    EventDiscussionHeaderView(
        titleText = "Event Discussion",
        closeIcon = R.drawable.ic_close_blue,
        primaryActionIcon = R.drawable.ic_email
    )
}
//endregion