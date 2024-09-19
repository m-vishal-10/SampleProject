package com.icebrekr.icebrekrui.uielements.textfield.custom

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icebrekr.icebrekrui.configurables.FontType
import com.icebrekr.icebrekrui.configurables.IBCustomFonts
import com.icebrekr.icebrekrui.configurables.IBFonts
import com.icebrekr.icebrekrui.configurables.color.IBTheme
import com.icebrekr.icebrekrui.helper.PackageImage
import com.icebrekr.uicomponents.components.textfield.BaseTextField
import com.icebrekr.uicomponents.components.textfield.properties.TextFieldProperties
import com.icebrekr.uicomponents.components.textfield.properties.TextFieldState
import com.icebrekr.uicomponents.components.textfield.properties.TextFieldStyleProperties
import com.icebrekr.utilities.validator.EmailTextValidator
import com.icebrekr.utilities.validator.TextValidator

@Composable
fun PrimaryTextField(
    text: String,
    placeHolder: String,
    isSecure: Boolean = false,
    title: String,
    infoTextProperties: TextFieldProperties.Info,
    validator: TextValidator,
    textFieldState: TextFieldState = TextFieldState.DEFAULT,
    primaryActionImage: Int? = null,
    secondaryActionImage: Int? = null,
    textChangeHandler: (String) -> Unit,
    primaryButtonAction:((String) -> Unit)? = null,
    secondaryButtonAction:((String) -> Unit)? = null
) {

    val text1 = remember { mutableStateOf("") }
    text1.value = text

    val textProperties = TextFieldProperties(
        text = text1.value,
        placeholder = placeHolder,
        title = title,
        info = infoTextProperties
    )

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

    BaseTextField(
        textProperties = textProperties,
        styleProperties = styleProperties,
        validator,
        isSecure = isSecure, // Set the state to disabled
        textFieldState = textFieldState,
        textChangeHandler = textChangeHandler,
        primaryButtonAction = primaryButtonAction,
        secondaryButtonAction = secondaryButtonAction
    )

}

@Preview
@Composable
fun PrimaryTextFieldContentView() {
    PrimaryTextField(
        text = "Primary",
        placeHolder = "Enter text here",
        title = "Label",
        infoTextProperties = TextFieldProperties.Info(
            defaultMessage = "Default message",
            successMessage = "Success message",
            errorMessage = "Error message",
            disabledMessage = "Disabled message",
            warningMessage = "Warning message"
        ),
        validator = EmailTextValidator(),
        textChangeHandler = {

        }
    )
}