package com.icebrekr.icebrekrui.uitryouts.banner

import ClickableLabel
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icebrekr.icebrekrui.R
import com.icebrekr.icebrekrui.configurables.FontType
import com.icebrekr.icebrekrui.configurables.IBCustomFonts
import com.icebrekr.icebrekrui.configurables.IBFonts
import com.icebrekr.icebrekrui.configurables.color.IBTheme
import com.icebrekr.icebrekrui.uielements.label.PrimaryLabel

@Composable
fun BannerView(titleText: String,
               speakerImage: Int = R.drawable.motta,
               subTitleText: String,
               ){
Row(modifier =Modifier.fillMaxWidth()
    .padding(12.dp),) {
    Image(painter = painterResource(id = speakerImage),
        contentDescription = "",
        modifier = Modifier
            .size(54.dp)
            .align(Alignment.CenterVertically))
    Spacer(modifier = Modifier.width(10.dp))
    Column {
        PrimaryLabel(
            text = titleText,
            font = IBFonts.appliedFont(IBCustomFonts.bold, FontType.title3),
            foregroundColor = IBTheme.theme.primary1Default,
            lineLimit = 1
        )
        PrimaryLabel(
            text = subTitleText,
            font = IBFonts.appliedFont(IBCustomFonts.regular, FontType.body),
            foregroundColor = IBTheme.theme.primary1Default,
            lineLimit = 1
        )
        ClickableLabel(
            fullText="Download the Outline",
            subText = "Download the Outline",
            font = IBFonts.appliedFont(IBCustomFonts.bold, FontType.caption1),
            textColor = IBTheme.theme.primary2Default,
            boldUnderlineColor = IBTheme.theme.primary2Default)
    }

}
}

@Preview(showBackground = true)
@Composable
fun PreviewBanner(){
    BannerView(titleText = "The power of becoming KNOWN",
        R.drawable.ic_person,
        subTitleText = "Discover the most effective strategies for becoming known")

}