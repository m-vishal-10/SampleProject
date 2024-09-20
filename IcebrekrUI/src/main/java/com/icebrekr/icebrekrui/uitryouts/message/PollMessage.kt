package com.icebrekr.icebrekrui.uitryouts.message

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
fun PollMessage(
    pfp: Painter,
    author: String,
    question: String,
    options: Array<String>,
    votes: IntArray,
    time: String
) {
    val totalVotes = votes.sum()

    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = pfp,
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .padding(end = 8.dp)
                .align(Alignment.Top)
        )
        Column(
            modifier = Modifier
                .padding(start = 9.dp, end = 9.dp)
                .weight(1f)
        ) {
            PrimaryLabel(
                text = author,
                font = IBFonts.appliedFont(IBCustomFonts.bold, FontType.headerBold),
                foregroundColor = IBTheme.theme.secondaryDefault
            )
            PrimaryLabel(
                text = question,
                font = IBFonts.appliedFont(IBCustomFonts.bold, FontType.subheadline),
                foregroundColor = IBTheme.theme.content1,
                lineLimit = Int.MAX_VALUE
            )

            val selectedOptionIndex = remember { mutableStateOf(1) }

            options.forEachIndexed { index, option -> // Only one loop needed
                Column(modifier = Modifier.padding(vertical = 4.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        RadioButton(
                            selected = selectedOptionIndex.value == index,
                            colors = RadioButtonDefaults.colors(IBTheme.theme.primary1Default),
                            onClick = { selectedOptionIndex.value = index }
                        )
                        Text(text = option, modifier = Modifier.weight(1f))

                        Text(
                            text = "${votes[index]} ",
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .height(7.dp)
                            .background(color = IBTheme.theme.grey3, shape = RoundedCornerShape(26.dp))
                            .border(
                                width = 1.dp,
                                color = IBTheme.theme.success,
                                shape = CircleShape
                            )
                    ) {
                        LinearProgressIndicator(
                            progress = if (totalVotes > 0) votes[index].toFloat() / totalVotes else 0f,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(7.dp),
                            color = IBTheme.theme.success, 
                            backgroundColor = IBTheme.theme.grey3
                        )
                    }
                }
            }



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
fun PollMessagePreview(){
    PollMessage(painterResource(R.drawable.ic_placeholder),"Alice (Host)","Would you sign up for Part 2?",
        arrayOf("Yes","No","Maybe"),votes = intArrayOf(1, 2, 1),"23:22 pm")
}