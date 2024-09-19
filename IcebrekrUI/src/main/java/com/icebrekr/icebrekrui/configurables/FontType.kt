package com.icebrekr.icebrekrui.configurables

import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

//MARK: - Font Type
/// Enumeration representing various font styles with associated raw string values.
enum class FontType(val fontSize: TextUnit) {
    largeTitle(34.sp),
    largeTitleBold(34.sp),
    title1(28.sp),
    TitleBold1(28.sp),
    title(24.sp),
    title2(22.sp),
    titleBold2(22.sp),
    title3(20.sp),
    titleBold3(20.sp),
    headerSemiBold(17.sp),
    headerBold(17.sp),
    body(17.sp),
    bodyBold(17.sp),
    callout(16.sp),
    calloutBold(16.sp),
    subheadline(15.sp),
    subheadlineBold(15.sp),
    footnote(13.sp),
    footnoteBold(13.sp),
    caption1(12.sp),
    caption1Bold(12.sp),
    caption2(11.sp),
    caption2Bold(11.sp);
}
