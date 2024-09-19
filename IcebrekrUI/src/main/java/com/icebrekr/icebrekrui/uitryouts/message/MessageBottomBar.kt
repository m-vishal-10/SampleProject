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
import com.icebrekr.utilities.validator.FreeTextFieldValidator
import com.icebrekr.utilities.validator.TextValidator

@Composable
fun MessageTextField(
    text: String = "",
    textChangeHandler: (String) -> Unit,
    onSendClick: (String) -> Unit,
    validator: TextValidator
) {
    var messageText by remember { mutableStateOf(text) }
    val showPlaceholder = messageText.isEmpty()

    Row(modifier = Modifier.padding(0.dp)){
    TextField(
        value = messageText,
        onValueChange = {
            messageText = it
            textChangeHandler(it)
        },
        placeholder = { if (showPlaceholder) androidx.compose.material.Text("Message") },
        modifier = Modifier
            .fillMaxWidth(),
        trailingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_right_check), // Replace with your icon
                contentDescription = "Send",
                modifier = Modifier.padding(end = 8.dp),
                tint = Color.Blue // You can choose your icon color here
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent, // Removing background and border
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMessageTextField() {
    MessageTextField(
        text = "",
        textChangeHandler = { /* handle text change */ },
        onSendClick = { /* handle send click */ },
        validator = FreeTextFieldValidator()
    )
}
