package com.icebrekr.icebrekrui.uielements.textfield.custom

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icebrekr.uicomponents.components.textfield.properties.TextFieldProperties
import com.icebrekr.uicomponents.components.textfield.properties.TextFieldState
import com.icebrekr.utilities.validator.EmailTextValidator
import com.icebrekr.utilities.validator.InfoTextFieldValidator
import com.icebrekr.utilities.validator.LargeInfoTextFieldValidator
import com.icebrekr.utilities.validator.MobileTextFieldValidator
import com.icebrekr.utilities.validator.NameTextValidator
import com.icebrekr.utilities.validator.PasswordTextFieldValidator
import com.icebrekr.utilities.validator.WebLinkTextFieldValidator

@Composable
fun EmailTextField(
    text: String = "",
    placeHolder: String = "Email",
    title: String = "Please enter the Email",
    textFieldState: TextFieldState = TextFieldState.DEFAULT,
    textChangeHandler: (String) -> Unit
) {
    val text1 = remember { mutableStateOf("") }
    text1.value = text
    var info = TextFieldProperties.Info(
        defaultMessage = null,
        successMessage = null,
        errorMessage = "Invalid Email",
        disabledMessage = null,
        warningMessage = null
    )

    PrimaryTextField(
        text = text1.value,
        placeHolder = placeHolder,
        title = title,
        infoTextProperties = info,
        validator = EmailTextValidator(),
        textFieldState = textFieldState,
        textChangeHandler = textChangeHandler
    )
}

@Composable
fun MobileTextField(
    text: String = "",
    placeHolder: String = "Mobile Number",
    title: String = "Please enter the Mobile Number",
    textFieldState: TextFieldState = TextFieldState.DEFAULT,
    primaryActionImage: Int? = null,
    secondaryActionImage: Int? = null,
    textChangeHandler: (String) -> Unit,
    primaryButtonAction:((String) -> Unit)? = null,
    secondaryButtonAction:((String) -> Unit)? = null
) {
    val text1 = remember { mutableStateOf("") }
    text1.value = text
    var info = TextFieldProperties.Info(
        defaultMessage = null,
        successMessage = null,
        errorMessage = "Invalid Mobile Number",
        disabledMessage = null,
        warningMessage = null
    )

    PrimaryTextField(
        text = text1.value,
        placeHolder = placeHolder,
        title = title,
        infoTextProperties = info,
        validator = MobileTextFieldValidator(),
        textFieldState = textFieldState,
        primaryActionImage = primaryActionImage,
        secondaryActionImage = secondaryActionImage,
        textChangeHandler = textChangeHandler,
        primaryButtonAction = primaryButtonAction,
        secondaryButtonAction = secondaryButtonAction
    )
}

@Composable
fun NameTextField(
    text: String = "",
    placeHolder: String = "Full Name",
    title: String = "Please enter your Full Name",
    textChangeHandler: (String) -> Unit
) {
    val text1 = remember { mutableStateOf("") }
    text1.value = text
    var info = TextFieldProperties.Info(
        defaultMessage = null,
        successMessage = null,
        errorMessage = "Invalid Name",
        disabledMessage = null,
        warningMessage = null
    )

    PrimaryTextField(
        text = text1.value,
        placeHolder = placeHolder,
        title = title,
        infoTextProperties = info,
        validator = NameTextValidator(),
        textChangeHandler = textChangeHandler
    )
}

@Composable
fun InfoTextField(
    text: String = "",
    placeHolder: String = "Full Name",
    title: String = "Please enter your Full Name",
    allowsEmptyInput: Boolean = true,
    textFieldState: TextFieldState = TextFieldState.DEFAULT,
    primaryActionImage: Int? = null,
    secondaryActionImage: Int? = null,
    textChangeHandler: (String) -> Unit,
    primaryButtonAction:((String) -> Unit)? = null,
    secondaryButtonAction:((String) -> Unit)? = null
) {
    val text1 = remember { mutableStateOf("") }
    text1.value = text
    var info = TextFieldProperties.Info(
        defaultMessage = null,
        successMessage = null,
        errorMessage = "Invalid Input",
        disabledMessage = null,
        warningMessage = null
    )

    val textValidator = InfoTextFieldValidator()
    textValidator.allowEmptyInput = allowsEmptyInput

    PrimaryTextField(
        text = text1.value,
        placeHolder = placeHolder,
        title = title,
        infoTextProperties = info,
        validator = textValidator,
        textFieldState = textFieldState,
        primaryActionImage = primaryActionImage,
        secondaryActionImage = secondaryActionImage,
        textChangeHandler = textChangeHandler,
        primaryButtonAction = primaryButtonAction,
        secondaryButtonAction = secondaryButtonAction
    )
}

@Composable
fun InfoTextArea(
    text: String = "",
    placeHolder: String = "Enter the Notes",
    title: String = "Notes",
    primaryActionImage: Int? = null,
    secondaryActionImage: Int? = null,
    textChangeHandler: (String) -> Unit,
    primaryButtonAction:((String) -> Unit)? = null,
    secondaryButtonAction:((String) -> Unit)? = null
) {
    val text1 = remember { mutableStateOf("") }
    text1.value = text
    var info = TextFieldProperties.Info(
        defaultMessage = null,
        successMessage = null,
        errorMessage = "Invalid Input",
        disabledMessage = null,
        warningMessage = null
    )

    PrimaryTextArea(
        text = text1.value,
        placeHolder = placeHolder,
        title = title,
        infoTextProperties = info,
        validator = LargeInfoTextFieldValidator(),
        primaryActionImage = primaryActionImage,
        secondaryActionImage = secondaryActionImage,
        textChangeHandler = textChangeHandler,
        primaryButtonAction = primaryButtonAction,
        secondaryButtonAction = secondaryButtonAction
    )
}

@Composable
fun PasswordTextField(
    text: String = "",
    placeHolder: String = "Password",
    title: String = "Please enter your Password",
    textChangeHandler: (String) -> Unit
) {
    val text1 = remember { mutableStateOf("") }
    text1.value = text
    var info = TextFieldProperties.Info(
        defaultMessage = null,
        successMessage = null,
        errorMessage = "Invalid Password",
        disabledMessage = null,
        warningMessage = null
    )

    PrimaryTextField(
        text = text1.value,
        placeHolder = placeHolder,
        title = title,
        isSecure = true,
        infoTextProperties = info,
        validator = PasswordTextFieldValidator(),
        textChangeHandler = textChangeHandler
    )
}

@Composable
fun LinkTextField(
    text: String = "",
    placeHolder: String = "Enter the Link",
    title: String = "Link",
    textChangeHandler: (String) -> Unit
) {
    val text1 = remember { mutableStateOf("") }
    text1.value = text
    var info = TextFieldProperties.Info(
        defaultMessage = null,
        successMessage = null,
        errorMessage = "Invalid Link",
        disabledMessage = null,
        warningMessage = null
    )

    PrimaryTextField(
        text = text1.value,
        placeHolder = placeHolder,
        title = title,
        infoTextProperties = info,
        validator = WebLinkTextFieldValidator(),
        textChangeHandler = textChangeHandler
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewBaseTextField() {
    Column(modifier = Modifier.padding(PaddingValues(all = 16.dp))) {
        EmailTextField(
            text = "john.doe@example.com",
            textChangeHandler = {

            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        MobileTextField(
            text = "1234567890",
            textChangeHandler = {

            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        NameTextField(
            text = "John Doe",
            textChangeHandler = {

            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        PasswordTextField(
            text = "securePassword",
            textChangeHandler = {
                // Handle text changes if needed
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        LinkTextField(
            text = "www.linkedin.com",
            textChangeHandler = {
                // Handle text changes if needed
            }
        )
    }
}