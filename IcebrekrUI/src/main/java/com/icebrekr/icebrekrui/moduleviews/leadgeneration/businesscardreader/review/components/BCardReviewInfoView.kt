package com.icebrekr.icebrekrui.moduleviews.leadgeneration.businesscardreader.review.components

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icebrekr.icebrekrui.configurables.FontType
import com.icebrekr.icebrekrui.configurables.IBCustomFonts
import com.icebrekr.icebrekrui.configurables.IBFonts
import com.icebrekr.icebrekrui.configurables.color.IBTheme
import com.icebrekr.icebrekrui.helper.PackageImage
import com.icebrekr.icebrekrui.uielements.label.PrimaryLabel
import com.icebrekr.uicomponents.components.buttons.ImageButton
import com.icebrekr.uicomponents.extensions.view.bordered

//region Review Info View
@Composable
fun BCardReviewInfoView(
    yesAction: (() -> Unit)? = null,
    noAction: (() -> Unit)? = null,
) {
    Box(
        modifier = Modifier
            .background(IBTheme.theme.grey6)
            .bordered(
                5.dp,
                1.dp,
                IBTheme.theme.grey6
            )
            .padding(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.Start
        ) {
            TextViews()
            ButtonViews(yesAction = yesAction, noAction = noAction)
        }
    }
}
//endregion

//region TextViews
@Composable
private fun TextViews() {
    Column(
        verticalArrangement = Arrangement.spacedBy(0.dp),
        horizontalAlignment = Alignment.Start
    ) {
        PrimaryLabel(
            text = "Choose correct matches",
            font = IBFonts.appliedFont(customFont = IBCustomFonts.bold, type = FontType.title3),
            foregroundColor = IBTheme.theme.primary1Default
        )
        PrimaryLabel(
            text = "You can also edit these details later",
            font = IBFonts.appliedFont(
                customFont = IBCustomFonts.regular,
                type = FontType.caption1
            ),
            foregroundColor = IBTheme.theme.primary1Default
        )
    }
}
//endregion

//region ButtonViews
@Composable
private fun ButtonViews(
    yesAction: (() -> Unit)? = null,
    noAction: (() -> Unit)? = null,
) {
    var yesButtonClicked = false
    var noButtonClicked = false

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.clickable {
                if (!yesButtonClicked) {
                    yesAction?.invoke()
                }
                yesButtonClicked = true
                Handler(Looper.getMainLooper()).postDelayed(
                    {
                        yesButtonClicked = false
                    }, 2000
                )
            }
        ) {
            ImageButton(
                image = PackageImage.rightCheck.toImage(),
                foregroundColor = IBTheme.theme.primary1Default,
                dimension = Pair(20f, 20f),
                onTap = {
                    if (!yesButtonClicked) {
                        yesAction?.invoke()
                    }
                    yesButtonClicked = true
                    Handler(Looper.getMainLooper()).postDelayed(
                        {
                            yesButtonClicked = false
                        }, 2000
                    )
                }
            )
            PrimaryLabel(
                text = "Choose for me",
                font = IBFonts.appliedFont(
                    customFont = IBCustomFonts.bold,
                    type = FontType.callout
                ),
                foregroundColor = IBTheme.theme.primary1Default,
                textAlign = TextAlign.Center
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.clickable {
                if (!noButtonClicked) {
                    noAction?.invoke()
                }
                noButtonClicked = true
                Handler(Looper.getMainLooper()).postDelayed(
                    {
                        noButtonClicked = false
                    }, 2000
                )
            }
        ) {
            ImageButton(
                image = PackageImage.closeBlue.toImage(),
                foregroundColor = IBTheme.theme.primary1Default,
                dimension = Pair(16f, 16f),
                onTap = {
                    if (!noButtonClicked) {
                        noAction?.invoke()
                    }
                    noButtonClicked = true
                    Handler(Looper.getMainLooper()).postDelayed(
                        {
                            noButtonClicked = false
                        }, 2000
                    )
                }
            )
            PrimaryLabel(
                text = "I'll review",
                font = IBFonts.appliedFont(
                    customFont = IBCustomFonts.bold,
                    type = FontType.callout
                ),
                foregroundColor = IBTheme.theme.danger,
                textAlign = TextAlign.Center
            )
        }
    }
}
//endregion

//region Preview
@Preview
@Composable
fun BCardReviewInfoViewPreview() {
    BCardReviewInfoView(
        yesAction = {
            println("Yes button pressed")
        },
        noAction = {
            println("No button pressed")
        }
    )
}
//endregion