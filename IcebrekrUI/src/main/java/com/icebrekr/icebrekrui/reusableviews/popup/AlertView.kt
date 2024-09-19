package com.icebrekr.icebrekrui.reusableviews.popup

import ClickableLabel
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icebrekr.icebrekrui.configurables.FontType
import com.icebrekr.icebrekrui.configurables.IBCustomFonts
import com.icebrekr.icebrekrui.configurables.IBFonts
import com.icebrekr.icebrekrui.configurables.color.IBTheme
import com.icebrekr.icebrekrui.uielements.button.custom.Primary1Button
import com.icebrekr.icebrekrui.uielements.button.custom.Tertiary2Button
import com.icebrekr.icebrekrui.uielements.label.PrimaryLabel
import com.icebrekr.uicomponents.components.buttons.properties.ButtonState

//region AlertView Composable
@Composable
fun AlertView(
    titleText: String,
    descriptionText: String,
    subLinkDescriptionText: String? = null,
    primaryButtonText: String,
    secondaryButtonText: String,
    onPrimaryButtonClick: (() -> Unit),
    onSecondaryButtonClick: (() -> Unit),
    textClickAction: (() -> Unit)? = null
) {
    val backgroundOpacity = 0.5f
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(IBTheme.theme.black.copy(alpha = backgroundOpacity))
            .clickable { /* Do nothing - consuming click events */ },
        contentAlignment = Alignment.Center
    ) {
        Box(modifier = Modifier
            .padding(20.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colors.background)
        ) {
            Column(
                modifier = Modifier
                    .background(IBTheme.theme.white)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {

                PrimaryLabel(
                    titleText,
                    IBFonts.appliedFont(IBCustomFonts.bold, FontType.title3),
                    IBTheme.theme.content1,
                    TextAlign.Center
                )

                ClickableLabel(
                    fullText = descriptionText,
                    subText = subLinkDescriptionText,
                    font = IBFonts.appliedFont(IBCustomFonts.regular, FontType.callout),
                    textColor = IBTheme.theme.content1,
                    boldUnderlineColor = IBTheme.theme.content1
                ) {
                    textClickAction?.invoke()
                }
                Box(
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Primary1Button(
                        titleText = primaryButtonText,
                        buttonState = ButtonState.DEFAULT,
                        action = { onPrimaryButtonClick.invoke() })
                }

                Tertiary2Button(
                    titleText = secondaryButtonText,
                    buttonState = ButtonState.DEFAULT,
                    action = { onSecondaryButtonClick.invoke() })

            }
        }
    }
}
//endregion

@Composable
fun AlertContentView() {
    var showAlert = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (!showAlert.value) {
                Button(onClick = { showAlert.value = true }) {
                    Text(text = "Show Alert")
                }
            }
            if (showAlert.value) {
                AlertView(
                    titleText = "Delete Contacts",
                    descriptionText = "Are you sure you want to delete the selected contacts?",
                    primaryButtonText = "No, Don’t Delete",
                    secondaryButtonText = "Yes, Delete",
                    onPrimaryButtonClick = {
                        // Define what happens when the primary button is tapped
                        println("Cancelled deletion")
                        showAlert.value = false
                    },
                    onSecondaryButtonClick = {
                        // Define what happens when the secondary button is tapped
                        println("Cancelled deletion")
                        showAlert.value = false
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun AlertContentViewPreview() {
    AlertContentView()
}