import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icebrekr.icebrekrui.R
import com.icebrekr.icebrekrui.configurables.color.IBTheme
import com.icebrekr.uicomponents.components.buttons.ImageButton

@Composable
fun PrimarySearchBar(
    defaultText: String = "",
    placeholder: String = "Search",
    onTextChange: (String) -> Unit,
    backgroundColor: Color = Color.Transparent
) {
    val searchTextState = remember { mutableStateOf(defaultText) }

    //TODO: - To align with design components later
    TextField(
        value = searchTextState.value,
        onValueChange = { newText ->
            searchTextState.value = newText
            onTextChange(newText)
        },
        placeholder = { Text(text = placeholder) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .background(color = backgroundColor)
            .clip(RoundedCornerShape(16.dp)),
        colors = TextFieldDefaults.textFieldColors(
            textColor = IBTheme.theme.primary1Default,
            disabledTextColor = IBTheme.theme.primary2Disabled,
            backgroundColor = IBTheme.theme.grey5,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        singleLine = true,
        trailingIcon = {
            if (searchTextState.value.isNotEmpty()) {
                IconButton(
                    onClick = {},
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    ImageButton(
                        image = R.drawable.ic_close_button,
                        foregroundColor = IBTheme.theme.black,
                        dimension = Pair(20f, 20f),
                        onTap = {
                            searchTextState.value = ""
                            onTextChange("")
                        }
                    )
                }
            }
        },
        leadingIcon = {
            ImageButton(
                image = R.drawable.ic_search_bar,
                foregroundColor = IBTheme.theme.black,
                dimension = Pair(25f, 25f),
                onTap = {

                }
            )
        }
    )
}

@Preview
@Composable
fun SearchBarPreview() {
    PrimarySearchBar(
        defaultText = "",
        placeholder = "Search",
        onTextChange = { newText ->
            // Handle text change
        },
    )
}
