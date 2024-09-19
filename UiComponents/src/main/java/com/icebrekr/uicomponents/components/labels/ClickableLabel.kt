import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.icebrekr.uicomponents.FontCustom

//region Clickable Label
@Composable
fun ClickableLabel(
    fullText: String,
    subText: String? = null,
    font: FontCustom,
    textColor: Color = Color.Black,
    boldUnderlineColor: Color = Color.Blue,
    textAlignment: TextAlign = TextAlign.Center,
    lineLimit: Int? =null,
    action: (() -> Unit)? = null
) {
    val textStyle = TextStyle(fontFamily = font.fontFamily,  fontSize = font.fontSize, color = textColor, textAlign = textAlignment)

    val annotatedString = buildAnnotatedString {
        val startIndex = subText?.let { fullText.indexOf(it) } ?: -1
        val endIndex = startIndex + (subText?.length ?: 0)

        if (startIndex >= 0 && endIndex <= fullText.length && startIndex < endIndex) {
            append(fullText.substring(0, startIndex))
            withStyle(style = SpanStyle(color = boldUnderlineColor, fontSize = font.fontSize, fontWeight = FontWeight.Bold, textDecoration = TextDecoration.Underline)) {
                append(fullText.substring(startIndex, endIndex))
            }
            append(fullText.substring(endIndex))
        } else {
            withStyle(style = SpanStyle(color = textColor, fontSize = font.fontSize, fontFamily = font.fontFamily)) {
                append(fullText)
            }
        }
    }

    ClickableText(
        text = annotatedString,
        style = textStyle,
        onClick = { offset ->
            action?.invoke()
        },
        maxLines = lineLimit ?: Int.MAX_VALUE,
        modifier = Modifier.padding(0.dp)
    )

}
//endregion

//region Clickable Label - Preview
@Preview(showBackground = true)
@Composable
fun PreviewClickableLabelVariations() {
    Column {
        ClickableLabel(
            fullText = "Click here for more information.",
            subText = "here",
            font = FontCustom(FontFamily.Default, 16.sp),
            textColor = Color.Black,
            boldUnderlineColor = Color.Black,
            textAlignment = TextAlign.Center,
            action = { println("Clicked 'here'") }
        )

        ClickableLabel(
            fullText = "Discover more about Compose.",
            subText = "Compose",
            font = FontCustom(FontFamily.Default, 20.sp),
            textColor = Color.DarkGray,
            boldUnderlineColor = Color.Green,
            textAlignment = TextAlign.Start,
            action = { println("Clicked 'Compose'") }
        )

        ClickableLabel(
            fullText = "Learn more about Android development.",
            subText = "Android",
            font = FontCustom(FontFamily.Default, 18.sp),
            textColor = Color.Gray,
            boldUnderlineColor = Color.Magenta,
            textAlignment = TextAlign.End,
            action = { println("Clicked 'Android'") }
        )
    }
}
//endregion
