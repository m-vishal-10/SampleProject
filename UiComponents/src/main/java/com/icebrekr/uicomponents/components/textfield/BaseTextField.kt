package com.icebrekr.uicomponents.components.textfield

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icebrekr.uicomponents.R
import com.icebrekr.uicomponents.components.buttons.ImageButton
import com.icebrekr.uicomponents.components.textfield.properties.TextFieldProperties
import com.icebrekr.uicomponents.components.textfield.properties.TextFieldState
import com.icebrekr.uicomponents.components.textfield.properties.TextFieldStyleProperties
import com.icebrekr.utilities.validator.NameTextValidator
import com.icebrekr.utilities.validator.TextValidator

@Composable
fun BaseTextField(
    textProperties: TextFieldProperties,
    styleProperties: TextFieldStyleProperties,
    validator: TextValidator,
    textFieldState: TextFieldState = TextFieldState.DEFAULT,
    isSecure: Boolean = false,
    textChangeHandler: (String) -> Unit,
    primaryButtonAction:((String) -> Unit)? = null,
    secondaryButtonAction:((String) -> Unit)? = null
) {
    val text = remember { mutableStateOf("") }
    text.value = textProperties.text

    val _textFieldState = remember {
        mutableStateOf(textFieldState)
    }
    val isTextHidden = remember { mutableStateOf(true) }
    val isValid = remember { mutableStateOf(true) }
    val isEditing = remember { mutableStateOf(false) }

    Column() {
        TitleText(
            textProperties = textProperties,
            styleProperties = styleProperties,
            primaryButtonAction = primaryButtonAction,
            secondaryButtonAction = secondaryButtonAction
        )
        Spacer(modifier = Modifier.height(4.dp))
        Card(
            shape = RoundedCornerShape(styleProperties.dimension.cornerRadius),
            border = BorderStroke(
                width = styleProperties.dimension.strokeWidth,
                styleProperties.color.borderColor
            ),

            ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                TextField(
                    value = text.value,
                    onValueChange = { newText ->
                        var textValue = newText // Placeholder for the actual input text
                        val maxTextLength = validator.maxCharacterCount
                        if (textValue.length > maxTextLength) {
                            text.value = textValue.take(maxTextLength)
                        } else {
                            var textValue = validator.restrictCharacters(textValue)
                            textValue = validator.formatter?.invoke(textValue) ?: textValue
                            text.value = textValue
                        }
                        isValid.value = validator.validate(text.value)
                        if (isValid.value) {
                            _textFieldState.value = TextFieldState.DEFAULT
                        } else {
                            _textFieldState.value = TextFieldState.ERROR
                        }
                        textChangeHandler(text.value)
                    },
                    placeholder = { Text(text = textProperties.placeholder) },
                    enabled = textFieldState != TextFieldState.DISABLED,
                    visualTransformation = if (isTextHidden.value) VisualTransformation.None else PasswordVisualTransformation(),
                    modifier = Modifier
                        .weight(1f),
                    textStyle = TextStyle(
                        fontFamily = styleProperties.font?.textFont,
                        color = getTextColor(textFieldState, styleProperties)
                    ),
                    singleLine = true,
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = styleProperties.color.textColor,
                        disabledTextColor = getTextColor(textFieldState, styleProperties),
                        backgroundColor = backgroundColorForState(textFieldState, styleProperties),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    trailingIcon = {
                        if (text.value.isNotEmpty()) {
                            IconButton(
                                onClick = {},
                                modifier = Modifier.padding(end = 8.dp)
                            ) {
                                ImageButton(
                                    image = styleProperties.icon.closeImage,
                                    dimension = Pair(30f, 30f),
                                    onTap = {
                                        if (textFieldState != TextFieldState.DISABLED) {
                                            text.value = ""
                                            isValid.value = validator.validate(text.value)
                                            if (isValid.value) {
                                                _textFieldState.value = TextFieldState.DEFAULT
                                            } else {
                                                _textFieldState.value = TextFieldState.ERROR
                                            }
                                            textChangeHandler("")
                                        }
                                    }
                                )
                            }
                        }
                    }
                )
                //TODO: Handle Secure Text fields
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier.padding(end = 12.dp) // Equivalent to .padding(.trailing, 12) in SwiftUI
        ) {
            Row {
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "${text.value.length}/${validator.maxCharacterCount}",
                    fontFamily = styleProperties.font?.characterCountDescriptionFont,
                    color = styleProperties.color.characterCountDescriptionColor // Assuming gray color for character count description
                )
            }
        }
        infoForState(_textFieldState.value, textProperties)?.let { infoForState ->
            Text(
                text = infoForState,
                fontFamily = styleProperties.font?.characterCountDescriptionFont,
                color = colorForState(
                    textFieldState,
                    styleProperties
                ) // Assuming gray color for character count description
            )
        }
    }
}



@Composable
private fun TitleText(textProperties: TextFieldProperties, styleProperties: TextFieldStyleProperties, primaryButtonAction: ((String) -> Unit)?, secondaryButtonAction: ((String) -> Unit)?) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = textProperties.title,
            fontFamily = styleProperties.font?.titleFont,
            color = styleProperties.color.titleColor
        )

        Row(horizontalArrangement = Arrangement.spacedBy(0.dp)) {
            styleProperties.icon.primaryActionImage?.let { actionIcon ->
                IconButton(
                    onClick = {
                        primaryButtonAction?.invoke(textProperties.text)
                    },
                    enabled = true,
                ) {
                    Icon(
                        painter = painterResource(id = actionIcon),
                        contentDescription = null,  // Provide a description or use `null` if it's purely decorative
                        modifier = Modifier
                            .height(22.dp)
                            .width(22.dp)
                    )
                }

            }
            styleProperties.icon.secondaryActionImage?.let { actionIcon ->
                IconButton(
                    onClick = {
                        secondaryButtonAction?.invoke(textProperties.text)
                    },
                    enabled = true,
                ) {
                    Icon(
                        painter = painterResource(id = actionIcon),
                        contentDescription = null,
                        modifier = Modifier
                            .height(22.dp)
                            .width(22.dp)
                    )
                }
            }
        }
    }
}



@Composable
fun getTextColor(textFieldState: TextFieldState, styleProperties: TextFieldStyleProperties): Color {
    return when (textFieldState) {
        TextFieldState.DEFAULT, TextFieldState.ERROR, TextFieldState.WARNING, TextFieldState.SUCCESS ->
            // Return the color for default, error, warning, or success state
            styleProperties.color.textColor

        TextFieldState.DISABLED ->
            // Return the color for disabled state
            styleProperties.color.disabledForegroundColor
    }
}

@Composable
fun backgroundColorForState(
    textFieldState: TextFieldState,
    styleProperties: TextFieldStyleProperties
): Color {
    return when (textFieldState) {
        TextFieldState.DEFAULT, TextFieldState.ERROR, TextFieldState.WARNING, TextFieldState.SUCCESS ->
            styleProperties.color.backgroundColor

        TextFieldState.DISABLED ->
            styleProperties.color.disabledBackgroundColor
    }
}

@Composable
fun foregroundColorForState(
    textFieldState: TextFieldState,
    styleProperties: TextFieldStyleProperties
): Color {
    return when (textFieldState) {
        TextFieldState.DEFAULT, TextFieldState.ERROR, TextFieldState.WARNING, TextFieldState.SUCCESS ->
            styleProperties.color.textColor

        TextFieldState.DISABLED ->
            styleProperties.color.disabledForegroundColor
    }
}

fun infoForState(textFieldState: TextFieldState, textProperties: TextFieldProperties): String? {
    return when (textFieldState) {
        TextFieldState.DEFAULT -> textProperties.info?.defaultMessage
        TextFieldState.ERROR -> textProperties.info?.errorMessage
        TextFieldState.WARNING -> textProperties.info?.warningMessage
        TextFieldState.SUCCESS -> textProperties.info?.successMessage
        TextFieldState.DISABLED -> textProperties.info?.disabledMessage
    }
}

@Composable
fun colorForState(
    textFieldState: TextFieldState,
    styleProperties: TextFieldStyleProperties
): Color {
    return when (textFieldState) {
        TextFieldState.DEFAULT -> styleProperties.color.indicationColors.defaultColor
        TextFieldState.ERROR -> styleProperties.color.indicationColors.errorColor
        TextFieldState.WARNING -> styleProperties.color.indicationColors.warningColor
        TextFieldState.SUCCESS -> styleProperties.color.indicationColors.successColor
        TextFieldState.DISABLED -> styleProperties.color.indicationColors.disabledColor
    }
}

@Composable
fun BaseTextFieldDefaultView() {
    val textProperties = TextFieldProperties(
        text = "",
        placeholder = "Username",
        title = "Username",
        info = TextFieldProperties.Info(
            defaultMessage = "Default",
            successMessage = "This is success",
            errorMessage = "This is error",
            disabledMessage = "Disabled",
            warningMessage = "This is warning"
        )
    )

    val styleProperties = TextFieldStyleProperties(
        font = TextFieldStyleProperties.FontStyle(
            textFont = FontFamily.Default,
            titleFont = FontFamily.Default,
            characterCountDescriptionFont = FontFamily.Default,
            infoFont = FontFamily.Default
        ),
        color = TextFieldStyleProperties.ColorStyle(
            textColor = Color.Black,
            backgroundColor = Color.Transparent,
            disabledForegroundColor = Color.Black,
            disabledBackgroundColor = Color.Gray,
            titleColor = Color.Black,
            characterCountDescriptionColor = Color.Gray,
            indicationColors = TextFieldStyleProperties.IndicationStyle(
                defaultColor = Color.Gray,
                errorColor = Color.Red,
                warningColor = Color.Yellow,
                successColor = Color.Green,
                disabledColor = Color.Transparent,
            ),
            borderColor = Color.Blue
        ),
        icon = TextFieldStyleProperties.IconStyle(
            closeImage = R.drawable.ic_back_arrow,
            secureOpenImage = R.drawable.ic_back_arrow,
            secureCloseImage = R.drawable.ic_back_arrow,
            primaryActionImage = R.drawable.ic_back_arrow,
            secondaryActionImage = R.drawable.ic_back_arrow
        ),
        dimension = TextFieldStyleProperties.DimensionStyle(
            height = 35.dp,
            cornerRadius = 16.dp,
            strokeWidth = 1.dp
        )
    )

    val validator = NameTextValidator()

    BaseTextField(
        textProperties = textProperties,
        styleProperties = styleProperties,
        validator,
        isSecure = false,
        textFieldState = TextFieldState.DEFAULT,
        textChangeHandler = {

        }
    )
}


@Composable
fun BaseTextFieldWarningView() {
    val textProperties = TextFieldProperties(
        text = "default",
        placeholder = "Username",
        title = "Username",
        info = TextFieldProperties.Info(
            defaultMessage = "Default",
            successMessage = "This is success",
            errorMessage = "This is error",
            disabledMessage = "Disabled",
            warningMessage = "This is warning"
        )
    )

    val styleProperties = TextFieldStyleProperties(
        font = TextFieldStyleProperties.FontStyle(
            textFont = FontFamily.Default,
            titleFont = FontFamily.Default,
            characterCountDescriptionFont = FontFamily.Default,
            infoFont = FontFamily.Default
        ),
        color = TextFieldStyleProperties.ColorStyle(
            textColor = Color.Black,
            backgroundColor = Color.Transparent,
            disabledForegroundColor = Color.Black,
            disabledBackgroundColor = Color.Gray,
            titleColor = Color.Black,
            characterCountDescriptionColor = Color.Gray,
            indicationColors = TextFieldStyleProperties.IndicationStyle(
                defaultColor = Color.Gray,
                errorColor = Color.Red,
                warningColor = Color.Yellow,
                successColor = Color.Green,
                disabledColor = Color.Transparent
            ),
            borderColor = Color.Blue
        ),
        icon = TextFieldStyleProperties.IconStyle(
            closeImage = R.drawable.ic_back_arrow,
            secureOpenImage = R.drawable.ic_back_arrow,
            secureCloseImage = R.drawable.ic_back_arrow,
            primaryActionImage = R.drawable.ic_back_arrow,
            secondaryActionImage = R.drawable.ic_back_arrow
        ),
        dimension = TextFieldStyleProperties.DimensionStyle(
            height = 56.dp,
            cornerRadius = 16.dp,
            strokeWidth = 1.dp
        )
    )

    val validator = NameTextValidator() // Assume this implements some validation logic

    BaseTextField(
        textProperties = textProperties,
        styleProperties = styleProperties,
        validator,
        isSecure = false,
        textFieldState = TextFieldState.WARNING,
        textChangeHandler = {

        }
    )
}

@Composable
fun BaseTextFieldErrorView() {
    val textProperties = TextFieldProperties(
        text = "default",
        placeholder = "Username",
        title = "Username",
        info = TextFieldProperties.Info(
            defaultMessage = "Default",
            successMessage = "This is success",
            errorMessage = "This is error",
            disabledMessage = "Disabled",
            warningMessage = "This is warning"
        )
    )

    val styleProperties = TextFieldStyleProperties(
        font = TextFieldStyleProperties.FontStyle(
            textFont = FontFamily.Default,
            titleFont = FontFamily.Default,
            characterCountDescriptionFont = FontFamily.Default,
            infoFont = FontFamily.Default
        ),
        color = TextFieldStyleProperties.ColorStyle(
            textColor = Color.Black,
            backgroundColor = Color.Transparent,
            disabledForegroundColor = Color.Black,
            disabledBackgroundColor = Color.Gray,
            titleColor = Color.Black,
            characterCountDescriptionColor = Color.Gray,
            indicationColors = TextFieldStyleProperties.IndicationStyle(
                defaultColor = Color.Gray,
                errorColor = Color.Red,
                warningColor = Color.Yellow,
                successColor = Color.Green,
                disabledColor = Color.Transparent
            ),
            borderColor = Color.Blue
        ),
        icon = TextFieldStyleProperties.IconStyle(
            closeImage = R.drawable.ic_back_arrow,
            secureOpenImage = R.drawable.ic_back_arrow,
            secureCloseImage = R.drawable.ic_back_arrow,
            primaryActionImage = R.drawable.ic_back_arrow,
            secondaryActionImage = R.drawable.ic_back_arrow
        ),
        dimension = TextFieldStyleProperties.DimensionStyle(
            height = 56.dp,
            cornerRadius = 16.dp,
            strokeWidth = 1.dp
        )
    )

    val validator = NameTextValidator() // Assume this implements some validation logic

    BaseTextField(
        textProperties = textProperties,
        styleProperties = styleProperties,
        validator,
        isSecure = false,
        textFieldState = TextFieldState.ERROR, // Set the state to error
        textChangeHandler = {
            // Implement your logic to handle text change
        }
    )
}

@Composable
fun BaseTextFieldSuccessView() {
    val textProperties = TextFieldProperties(
        text = "default",
        placeholder = "Username",
        title = "Username",
        info = TextFieldProperties.Info(
            defaultMessage = "Default",
            successMessage = "This is success",
            errorMessage = "This is error",
            disabledMessage = "Disabled",
            warningMessage = "This is warning"
        )
    )

    val styleProperties = TextFieldStyleProperties(
        font = TextFieldStyleProperties.FontStyle(
            textFont = FontFamily.Default,
            titleFont = FontFamily.Default,
            characterCountDescriptionFont = FontFamily.Default,
            infoFont = FontFamily.Default
        ),
        color = TextFieldStyleProperties.ColorStyle(
            textColor = Color.Black,
            backgroundColor = Color.Transparent,
            disabledForegroundColor = Color.Black,
            disabledBackgroundColor = Color.Gray,
            titleColor = Color.Black,
            characterCountDescriptionColor = Color.Gray,
            indicationColors = TextFieldStyleProperties.IndicationStyle(
                defaultColor = Color.Gray,
                errorColor = Color.Red,
                warningColor = Color.Yellow,
                successColor = Color.Green,
                disabledColor = Color.Transparent
            ),
            borderColor = Color.Blue
        ),
        icon = TextFieldStyleProperties.IconStyle(
            closeImage = R.drawable.ic_back_arrow,
            secureOpenImage = R.drawable.ic_back_arrow,
            secureCloseImage = R.drawable.ic_back_arrow,
            primaryActionImage = R.drawable.ic_back_arrow,
            secondaryActionImage = R.drawable.ic_back_arrow
        ),
        dimension = TextFieldStyleProperties.DimensionStyle(
            height = 56.dp,
            cornerRadius = 16.dp,
            strokeWidth = 1.dp
        )
    )

    val validator = NameTextValidator() // Implement validation logic accordingly

    BaseTextField(
        textProperties = textProperties,
        styleProperties = styleProperties,
        validator,
        isSecure = false,
        textFieldState = TextFieldState.SUCCESS, // Set the state to success
        textChangeHandler = {
            // Handle text change
        }
    )
}

@Composable
fun BaseTextFieldDisabledView() {
    val textProperties = TextFieldProperties(
        text = "default",
        placeholder = "Username",
        title = "Username",
        info = TextFieldProperties.Info(
            defaultMessage = "Default",
            successMessage = "This is success",
            errorMessage = "This is error",
            disabledMessage = "Disabled",
            warningMessage = "This is warning"
        )
    )

    val styleProperties = TextFieldStyleProperties(
        font = TextFieldStyleProperties.FontStyle(
            textFont = FontFamily.Default,
            titleFont = FontFamily.Default,
            characterCountDescriptionFont = FontFamily.Default,
            infoFont = FontFamily.Default
        ),
        color = TextFieldStyleProperties.ColorStyle(
            textColor = Color.Black,
            backgroundColor = Color.Transparent,
            disabledForegroundColor = Color.Black,
            disabledBackgroundColor = Color.Gray,
            titleColor = Color.Black,
            characterCountDescriptionColor = Color.Gray,
            indicationColors = TextFieldStyleProperties.IndicationStyle(
                defaultColor = Color.Gray,
                errorColor = Color.Red,
                warningColor = Color.Yellow,
                successColor = Color.Green,
                disabledColor = Color.Transparent
            ),
            borderColor = Color.Blue
        ),
        icon = TextFieldStyleProperties.IconStyle(
            closeImage = R.drawable.ic_back_arrow,
            secureOpenImage = R.drawable.ic_back_arrow,
            secureCloseImage = R.drawable.ic_back_arrow,
            primaryActionImage = R.drawable.ic_back_arrow,
            secondaryActionImage = R.drawable.ic_back_arrow
        ),
        dimension = TextFieldStyleProperties.DimensionStyle(
            height = 56.dp,
            cornerRadius = 16.dp,
            strokeWidth = 1.dp
        )
    )

    val validator = NameTextValidator() // Assume this implements some validation logic

    BaseTextField(
        textProperties = textProperties,
        styleProperties = styleProperties,
        validator,
        isSecure = false,
        textFieldState = TextFieldState.DISABLED, // Set the state to disabled
        textChangeHandler = {
            // Normally, you wouldn't handle text changes for a disabled field
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewBaseTextField() {
    Column(modifier = Modifier.padding(PaddingValues(all = 16.dp))) {
        BaseTextFieldDefaultView()
        Spacer(modifier = Modifier.height(16.dp))
        BaseTextFieldWarningView()
        Spacer(modifier = Modifier.height(16.dp))
        BaseTextFieldErrorView()
        Spacer(modifier = Modifier.height(16.dp))
        BaseTextFieldSuccessView()
        Spacer(modifier = Modifier.height(16.dp))
        BaseTextFieldDisabledView()
    }
}
