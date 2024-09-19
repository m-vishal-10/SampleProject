package com.icebrekr.uicomponents.components.searchbar.properties

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

// SearchBarProperties
data class SearchBarProperties(
    var searchText: String = "",
    var placeholder: String
)

// SearchBarStyleProperties
class SearchBarStyleProperties(
    val font: FontStyle,
    val color: ColorStyle,
    val icon: IconStyle,
    val dimension: DimensionStyle
) {
    // FontStyle
    data class FontStyle(
        val textFont: Int
    )

    // ColorStyle
    data class ColorStyle(
        val textColor: Color,
        val backgroundColor: Color,
        val disabledForegroundColor: Color,
        val disabledBackgroundColor: Color
    )

    // IconStyle
    data class IconStyle(
        val searchIcon: Int,
        val cancelIcon: Int
    )

    // DimensionStyle
    data class DimensionStyle(
        val height: Dp,
        val cornerRadius: Dp,
        val strokeWidth: Dp
    )
}

// SearchBarState
enum class SearchBarState {
    Default, Disabled
}
