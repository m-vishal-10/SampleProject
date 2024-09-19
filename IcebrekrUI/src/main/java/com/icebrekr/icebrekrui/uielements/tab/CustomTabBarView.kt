package com.icebrekr.icebrekrui.uielements.tab

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

@Composable
fun CustomTabBarView(
    selectedTab: MutableState<Int?>,
    tabInfo: List<Pair<Int, String>>
) {
    Box(
        modifier = Modifier
            .bordered(
                cornerRadius = 32.dp,
                borderWidth = 0.dp,
                borderColor = Color.Transparent
            )
            .background(IBTheme.theme.grey6)
            .padding(vertical = 4.dp, horizontal = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .background(IBTheme.theme.grey6)
                .bordered(
                    cornerRadius = 32.dp,
                    borderWidth = 0.dp,
                    borderColor = IBTheme.theme.grey6
                )
        ) {
            tabInfo.forEachIndexed { index, pair ->
                Box(
                    modifier = Modifier
                        .clickable {
                            selectedTab.value = index
                        }
                ) {
                    TabBarSectionView(
                        pair,
                        index,
                        selectedTab
                    )
                }
            }
        }
    }
}

@Composable
fun TabBarSectionView(
    tabInformation: Pair<Int, String>,
    index: Int,
    selectedTab: MutableState<Int?>
) {
    Box(
        modifier = Modifier
            .bordered(
                cornerRadius = 24.dp,
                borderWidth = 0.dp,
                borderColor = Color.Transparent
            )
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(
                    if (selectedTab.value == index) IBTheme.theme.primary1Default else Color.Transparent
                )
                .padding(vertical = 8.dp, horizontal = 12.dp)
                .bordered(
                    cornerRadius = 24.dp,
                    borderWidth = 0.dp,
                    borderColor = Color.Transparent
                )
        ) {
            Spacer(modifier = Modifier.width(2.dp))
            ImageButton(
                image = tabInformation.first,
                foregroundColor = if (selectedTab.value == index) IBTheme.theme.content2 else IBTheme.theme.primary1Default,
                dimension = Pair(20f, 20f),
                onTap = {
                    selectedTab.value = index
                }
            )
            Spacer(modifier = Modifier.width(4.dp))
            PrimaryLabel(
                text = tabInformation.second,
                font = IBFonts.appliedFont(
                    customFont = IBCustomFonts.bold,
                    type = FontType.subheadline
                ),
                foregroundColor = if (selectedTab.value == index) IBTheme.theme.content2 else IBTheme.theme.primary1Default,
                textAlign = TextAlign.Center,
                lineLimit = 1
            )
            Spacer(modifier = Modifier.width(2.dp))
        }
    }
}

@Composable
fun CustomTabBarContentView(selectedTab: MutableState<Int?>) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        CustomTabBarView(
            selectedTab = selectedTab,
            tabInfo = listOf(
                Pair(PackageImage.qRCode.toImage(), "QR"),
                Pair(PackageImage.bCard.toImage(), "BCard"),
                Pair(PackageImage.myQrCard.toImage(), "My QR")
            )
        )
    }
}

@Preview
@Composable
fun CustomTabBarViewPreview() {
    val selectedTab = remember {
        mutableStateOf<Int?>(2)
    }
    Box(
        modifier = Modifier.background(Color.Black)
    ) {
        CustomTabBarContentView(selectedTab)
    }
}
