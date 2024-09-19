package com.icebrekr.uicomponents.components.searchbar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

// Assuming definitions for these to match the Swift structures
data class SearchBarProperties(val placeholder: String)
data class SearchBarStyleProperties(
    val font: TextStyle,
    val color: Color,
    val icon: IconStyle,
    val dimension: DimensionStyle
)

data class IconStyle(val searchIcon: ImageVector, val cancelIcon: ImageVector)
data class DimensionStyle(val height: Float, val cornerRadius: Float, val strokeWidth: Float)

enum class SearchBarState {
    Default, Disabled
}

// Validator interface similar to TextFieldValidator
interface TextFieldValidator {
    fun validate(text: String): Boolean
    fun sanitize(text: String): String
    fun maxCharacterCount(): Int
}

@Composable
fun BaseSearchBar(
    properties: SearchBarProperties,
    styleProperties: SearchBarStyleProperties,
    state: SearchBarState,
    validator: TextFieldValidator,
    textChangeHandler: (String) -> Unit
) {
    var searchText by remember { mutableStateOf(TextFieldValue("")) }

    BasicTextField(
        value = searchText,
        onValueChange = {
            if (it.text.length <= validator.maxCharacterCount()) {
                searchText = it.copy(text = validator.sanitize(it.text))
                textChangeHandler(searchText.text)
            }
        },
        textStyle = styleProperties.font,
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .height(IntrinsicSize.Min)
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .background(Color.LightGray) // Example background
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    innerTextField()
                    Spacer(Modifier.width(8.dp))
                    Icon(Icons.Default.Search, contentDescription = "Search")
                    if (searchText.text.isNotEmpty()) {
                        Spacer(Modifier.width(8.dp))
                        Icon(Icons.Default.Close, contentDescription = "Clear", modifier = Modifier.clickable { searchText = TextFieldValue("") })
                    }
                }
            }
        }
    )
}

// Example usage of BaseSearchBar
@Preview(showBackground = false)
@Composable
fun SearchBarPreview() {
    val validator = object : TextFieldValidator {
        override fun validate(text: String): Boolean = true
        override fun sanitize(text: String): String = text.filter { it.isLetterOrDigit() }
        override fun maxCharacterCount(): Int = 20
    }

    BaseSearchBar(
        properties = SearchBarProperties(placeholder = "Search..."),
        styleProperties = SearchBarStyleProperties(
            font = TextStyle(color = Color.Black, fontSize = 16.sp),
            color = Color.Gray,
            icon = IconStyle(Icons.Default.Search, Icons.Default.Close),
            dimension = DimensionStyle(height = 56f, cornerRadius = 8f, strokeWidth = 1f)
        ),
        state = SearchBarState.Default,
        validator = validator,
        textChangeHandler = { println(it) }
    )
}
