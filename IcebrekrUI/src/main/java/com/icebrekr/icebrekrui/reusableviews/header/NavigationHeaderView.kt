import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icebrekr.icebrekrui.configurables.FontType
import com.icebrekr.icebrekrui.configurables.IBCustomFonts
import com.icebrekr.icebrekrui.configurables.IBFonts
import com.icebrekr.icebrekrui.configurables.color.IBTheme
import com.icebrekr.icebrekrui.uielements.label.PrimaryLabel
import com.icebrekr.icebrekrui.uielements.views.ScreenView
import com.icebrekr.uicomponents.R
import com.icebrekr.uicomponents.components.buttons.ImageButton


//region Navigation Action
enum class NavigationAction {
    backAction,
    primaryAction,
    secondaryAction
}
//endregion

//region Navigation Header View
@Composable
fun NavigationHeaderView(
    titleText: String,
    backActionIcon: Int = R.drawable.ic_back_arrow,
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
        ImageButton(image = backActionIcon, foregroundColor = IBTheme.theme.primary1Default) {
            action?.invoke(NavigationAction.backAction)
        }

        if (primaryActionIcon != null && secondaryActionIcon != null) {
            Spacer(modifier = Modifier.weight(1.5f))
        } else if (primaryActionIcon != null || secondaryActionIcon != null) {
            1.0f
            Spacer(modifier = Modifier.weight(1.0f))
        } else {
            Spacer(modifier = Modifier.weight(0.75f))
        }

        PrimaryLabel(
            text = titleText,
            font = IBFonts.appliedFont(IBCustomFonts.bold, FontType.title2),
            foregroundColor = IBTheme.theme.primary1Default
        )

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

// Navigation Header View - Preview
@Preview
@Composable
fun NavigationHeaderViewPreview() {
    NavigationHeaderView(
        titleText = "Contacts",
        backActionIcon = R.drawable.ic_back_arrow,
        primaryActionIcon = R.drawable.ic_back_arrow,
        secondaryActionIcon = R.drawable.ic_back_arrow
    )
}
