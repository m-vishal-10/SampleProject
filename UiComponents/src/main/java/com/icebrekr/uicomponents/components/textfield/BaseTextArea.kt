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
fun BaseTextArea(
    textProperties: TextFieldProperties,
    styleProperties: TextFieldStyleProperties,
    validator: TextValidator,
    isSecure: Boolean = false,
    textFieldState: TextFieldState = TextFieldState.DEFAULT,
    textChangeHandler: (String) -> Unit,
    primaryButtonAction:((String) -> Unit)? = null,
    secondaryButtonAction:((String) -> Unit)? = null
) {
    val text = remember { mutableStateOf(textProperties.text) }

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
                        textChangeHandler(text.value)
                    },
                    placeholder = { Text(text = textProperties.placeholder) },
                    enabled = textFieldState != TextFieldState.DISABLED,
                    visualTransformation = if (isTextHidden.value) VisualTransformation.None else PasswordVisualTransformation(),
                    modifier = Modifier
                        .height(100.dp),
                    textStyle = TextStyle(
                        fontFamily = styleProperties.font?.textFont,
                        color = getTextColor(textFieldState, styleProperties)
                    ),
                    singleLine = false,
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = styleProperties.color.textColor,
                        disabledTextColor = styleProperties.color.disabledBackgroundColor,
                        backgroundColor = styleProperties.color.backgroundColor,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    trailingIcon = {
                        //TODO: - REVIEW_CHECK Later
                        /*
                        if (text.value.isNotEmpty()) {
                            IconButton(
                                onClick = {},
                                modifier = Modifier.padding(end = 8.dp),
                            ) {
                                ImageButton(
                                    image = styleProperties.icon.closeImage,
                                    dimension = Pair(30f, 30f),
                                    onTap = {
                                        if (textFieldState != TextFieldState.DISABLED) {
                                            text.value = ""
                                        }
                                    }
                                )
                            }
                        }
                         */
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
        infoForState(textFieldState, textProperties)?.let { infoForState ->
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
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(bottom = 5.dp)
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
                            .height(15.dp)
                            .width(15.dp)
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
                        contentDescription = null,  // Provide a description or use `null` if it's purely decorative
                        modifier = Modifier
                            .height(15.dp)
                            .width(15.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun BaseTextAreaDefaultView() {
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

    BaseTextArea(
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
fun BaseTextAreaWarningView() {
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

    BaseTextArea(
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
fun BaseTextAreaErrorView() {
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

    BaseTextArea(
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
fun BaseTextAreaSuccessView() {
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

    BaseTextArea(
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
fun BaseTextAreaDisabledView() {
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

    BaseTextArea(
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
fun PreviewBaseTextArea() {
    Column(modifier = Modifier.padding(PaddingValues(all = 16.dp))) {
        BaseTextAreaDefaultView()
        Spacer(modifier = Modifier.height(16.dp))
        BaseTextAreaWarningView()
        Spacer(modifier = Modifier.height(16.dp))
        BaseTextAreaErrorView()
        Spacer(modifier = Modifier.height(16.dp))
        BaseTextAreaSuccessView()
        Spacer(modifier = Modifier.height(16.dp))
        BaseTextAreaDisabledView()
    }
}
