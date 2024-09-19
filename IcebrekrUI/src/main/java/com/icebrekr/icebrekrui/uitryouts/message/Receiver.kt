package com.icebrekr.icebrekrui.uitryouts.message

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icebrekr.icebrekrui.R
import com.icebrekr.icebrekrui.configurables.FontType
import com.icebrekr.icebrekrui.configurables.IBCustomFonts
import com.icebrekr.icebrekrui.configurables.IBFonts
import com.icebrekr.icebrekrui.configurables.color.IBTheme
import com.icebrekr.icebrekrui.uielements.label.PrimaryLabel
import kotlin.Int.Companion.MAX_VALUE

@Composable
fun Receiver(sender: String,message: String,time:String,pfp: Painter){

    Row (
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ){
        Image(
            painter = pfp,
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .padding(end = 8.dp)
                .align(Alignment.Top)
        )
        Column(modifier = Modifier
            .padding(start = 9.dp, end = 9.dp)
            .weight(1f)){
            PrimaryLabel(text = sender, font = IBFonts.appliedFont(
                IBCustomFonts.regular, FontType.headerBold
            ), foregroundColor = IBTheme.theme.secondaryDefault)
            PrimaryLabel(text = message, font = IBFonts.appliedFont(
                IBCustomFonts.regular, FontType.subheadline
            ), foregroundColor = IBTheme.theme.content1, lineLimit = MAX_VALUE
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                PrimaryLabel(
                    text = time,
                    font = IBFonts.appliedFont(IBCustomFonts.regular, FontType.caption1),
                    foregroundColor = IBTheme.theme.content1,
                    lineLimit = Int.MAX_VALUE
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReceiverPreview() {
    Receiver("Alice (Host)",
        "Hi everyone, excited for the event this Friday!\u2028Bob, could you help moderate the Q&A session?",
        "23:22 pm",
        painterResource(id = R.drawable.ic_placeholder)
    )
}