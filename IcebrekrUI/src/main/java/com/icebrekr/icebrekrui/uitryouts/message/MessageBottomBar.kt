package com.icebrekr.icebrekrui.uitryouts.message

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icebrekr.icebrekrui.R
import com.icebrekr.icebrekrui.configurables.FontType
import com.icebrekr.icebrekrui.configurables.IBCustomFonts
import com.icebrekr.icebrekrui.configurables.IBFonts
import com.icebrekr.icebrekrui.configurables.color.IBTheme
import com.icebrekr.icebrekrui.helper.PackageImage
import com.icebrekr.icebrekrui.uielements.textfield.custom.PrimaryTextField
import com.icebrekr.uicomponents.components.textfield.properties.TextFieldProperties
import com.icebrekr.uicomponents.components.textfield.properties.TextFieldStyleProperties
import com.icebrekr.utilities.validator.FreeTextFieldValidator
import com.icebrekr.utilities.validator.NameTextValidator
import com.icebrekr.utilities.validator.TextValidator

@Composable
fun MessageTextField(
    text: String = "",
    textChangeHandler: (String) -> Unit,
    primaryActionImage: Int? = null,
    primaryButtonAction: ((String) -> Unit)? = null,
    secondaryActionImage: Int? = null,
    secondaryButtonAction: ((String) -> Unit)? = null
) {

    var messageText by remember { mutableStateOf(text) }

    val indicationColorStyle = TextFieldStyleProperties.IndicationStyle(
        defaultColor = IBTheme.theme.grey1,
        errorColor = IBTheme.theme.danger,
        warningColor = IBTheme.theme.warning,
        successColor = IBTheme.theme.success,
        disabledColor = IBTheme.theme.grey1
    )
    val fontStyle = TextFieldStyleProperties.FontStyle(
        textFont = IBFonts.appliedFont(IBCustomFonts.bold, FontType.calloutBold).fontFamily,
        titleFont = IBFonts.appliedFont(IBCustomFonts.bold, FontType.calloutBold).fontFamily,
        characterCountDescriptionFont = IBFonts.appliedFont(
            IBCustomFonts.bold,
            FontType.footnoteBold
        ).fontFamily,
        infoFont = IBFonts.appliedFont(IBCustomFonts.bold, FontType.footnoteBold).fontFamily
    )

    val colorStyle = TextFieldStyleProperties.ColorStyle(
        textColor = IBTheme.theme.primary1Default,
        backgroundColor = IBTheme.theme.content2,
        disabledForegroundColor = IBTheme.theme.primary2Disabled,
        disabledBackgroundColor = IBTheme.theme.primary1Disabled,
        titleColor = IBTheme.theme.content1,
        characterCountDescriptionColor = IBTheme.theme.grey1,
        indicationColors = indicationColorStyle,
        borderColor = IBTheme.theme.primary1Default
    )

    val iconStyle = TextFieldStyleProperties.IconStyle(
        closeImage = PackageImage.closeCovered.toImage(),
        secureOpenImage = PackageImage.secureOpen.toImage(),
        secureCloseImage = PackageImage.secureClosed.toImage(),
        primaryActionImage = primaryActionImage,
        secondaryActionImage = secondaryActionImage
    )

    val dimensionStyle = TextFieldStyleProperties.DimensionStyle(
        height = 42.dp,
        cornerRadius = 16.dp,
        strokeWidth = 1.dp
    )

    val styleProperties = TextFieldStyleProperties(
        font = fontStyle,
        color = colorStyle,
        icon = iconStyle,
        dimension = dimensionStyle
    )
    PrimaryTextField(
        text = messageText,
        placeHolder = "Message",
        infoTextProperties = TextFieldProperties.Info(
            defaultMessage = null,
            successMessage = null,
            errorMessage = "Invalid Name",
            disabledMessage = null,
            warningMessage = null
        ),
        title = "",
        validator = NameTextValidator(),
        textChangeHandler = {
            messageText = it
            textChangeHandler(it)
        },
        primaryActionImage = primaryActionImage, // Pass the logo or icon here
        primaryButtonAction = primaryButtonAction
    )
}


@Preview(showBackground = true)
@Composable
fun PreviewMessageTextField() {
    MessageTextField(
        text = "",
        textChangeHandler = { /* handle text change */ },
        primaryActionImage = R.drawable.ic_check_blue
    )
}
