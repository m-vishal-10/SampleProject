package com.icebrekr.icebrekrui.configurables

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import com.icebrekr.icebrekrui.R
import com.icebrekr.uicomponents.FontCustom

//region IBCustom Fonts
enum class IBCustomFonts() {
    black,
    blackItalic,
    bold,
    boldItalic,
    extraBold,
    extraBoldItalic,
    extraLight,
    extraLightItalic,
    italic,
    light,
    lightItalic,
    medium,
    mediumItalic,
    regular,
    semiBold,
    semiBoldItalic,
    semiThin,
    semiThinItalic;

    fun toFont(): Int {
        val fontRes = when (this) {
            black -> R.font.notosans_black
            blackItalic -> R.font.notosans_blackitalic
            boldItalic -> R.font.notosans_bolditalic
            bold -> R.font.notosans_bold
            extraBold -> R.font.notosans_extrabold
            extraBoldItalic -> R.font.notosans_extrabolditalic
            extraLight -> R.font.notosans_extralight
            extraLightItalic -> R.font.notosans_extralightitalic
            italic -> R.font.notosans_italic
            light -> R.font.notosans_light
            lightItalic -> R.font.notosans_lightitalic
            medium -> R.font.notosans_medium
            mediumItalic -> R.font.notosans_mediumitalic
            regular -> R.font.notosans_regular
            semiBold -> R.font.notosans_semibold
            semiBoldItalic -> R.font.notosans_semibolditalic
            semiThin -> R.font.notosans_thin
            semiThinItalic -> R.font.notosans_thinitalic
        }
        return fontRes
    }

}
//endregion

//region IBFonts
object IBFonts {
    fun appliedFont(customFont: IBCustomFonts, type: FontType) : FontCustom {
        return FontCustom(FontFamily(Font(customFont.toFont())), type.fontSize)
    }
}
//endregion
