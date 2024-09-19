package com.icebrekr.utilities.validator

// region Interface for Text Field Validation
interface TextValidator {

    var maxCharacterCount: Int
    var allowEmptyInput: Boolean
    var formatter: ((String) -> String)?

    fun restrictCharacters(value: String): String
    fun validate(value: String): Boolean

    // Default formatter for phone numbers
    companion object {
        val phoneNumberFormatter: ((String) -> String)?
            get() = { rawInput ->
                val filteredInput = rawInput.filter { it.isDigit() || it == '+' }
                if (filteredInput.startsWith("+")) {
                    val endIndex = minOf(filteredInput.length, 16)
                    filteredInput.take(endIndex)
                } else {
                    val digitsOnly = filteredInput.filter { it.isDigit() }
                    if (digitsOnly.length >= 10) {
                        val areaCode = digitsOnly.take(3)
                        val secondPart = digitsOnly.substring(3, 6)
                        val lastPart = digitsOnly.takeLast(4)
                        "($areaCode) $secondPart-$lastPart"
                    } else {
                        digitsOnly
                    }
                }
            }
        val webLinkFormatter: ((String) -> String)?
            get() = { link ->
                if (link.isEmpty()) {
                    link
                } else if (link.startsWith("http://") || link.startsWith("https://")) {
                    link
                } else {
                    "https://$link"
                }
            }
    }

}
//endregion


//region Email Text Field Validator Implementation
class EmailTextValidator(
    override var maxCharacterCount: Int = 100,
    override var allowEmptyInput: Boolean = false,
    override var formatter: ((String) -> String)? = null
) : TextValidator {
    override fun restrictCharacters(value: String): String =
        value.filter { it.isLetterOrDigit() || it in "@.-" }

    override fun validate(value: String): Boolean {
        if (value.isEmpty() && allowEmptyInput) return true

        val emailRegex = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}"
        return value.matches(emailRegex.toRegex())
    }
}
//endregion

//region Name Text Field Validator Implementation
class NameTextValidator(
    override var maxCharacterCount: Int = 50,
    override var allowEmptyInput: Boolean = false,
    override var formatter: ((String) -> String)? = null
) : TextValidator {
    override fun restrictCharacters(value: String): String =
        value.replace(Regex("[^\\p{L}0-9 _-]"), "")

    override fun validate(value: String): Boolean {
        if (value.isEmpty() && allowEmptyInput) return true
        if (value.length > maxCharacterCount) return false

        val regexPattern = "^[\\p{L}0-9 _-]+$"
        return value.matches(regexPattern.toRegex())
    }
}
//endregion

//region Info Text Field Validator Implementation
class InfoTextFieldValidator(
    override var maxCharacterCount: Int = 100,
    override var allowEmptyInput: Boolean = false,
    override var formatter: ((String) -> String)? = null
) : TextValidator {
    override fun restrictCharacters(value: String): String = value  // No restrictions

    override fun validate(value: String): Boolean {
        if (value.isEmpty() && allowEmptyInput) return true
        return value.length <= maxCharacterCount
    }
}

class LargeInfoTextFieldValidator(
    override var maxCharacterCount: Int = 500,
    override var allowEmptyInput: Boolean = false,
    override var formatter: ((String) -> String)? = null
) : TextValidator {
    override fun restrictCharacters(value: String): String = value  // No restrictions

    override fun validate(value: String): Boolean {
        if (value.isEmpty() && allowEmptyInput) return true
        return value.length <= maxCharacterCount
    }
}
//endregion

//region Mobile Text Field Validator
class MobileTextFieldValidator(
    override var maxCharacterCount: Int = 16,
    override var allowEmptyInput: Boolean = false,
    override var formatter: ((String) -> String)? = TextValidator.phoneNumberFormatter
) : TextValidator {
    override fun restrictCharacters(value: String): String =
        value.filter { it.isDigit() || it == '+' }

    override fun validate(value: String): Boolean {
        if (value.isEmpty() && allowEmptyInput) return true
        return value.length <= maxCharacterCount && value.all { it.isDigit() || it == '+' }
    }
}
//endregion

//region Free Text Field Validator
class FreeTextFieldValidator(
    override var maxCharacterCount: Int = 50,
    override var allowEmptyInput: Boolean = false,
    override var formatter: ((String) -> String)? = null
) : TextValidator {
    override fun restrictCharacters(value: String): String = value

    override fun validate(value: String): Boolean {
        if (value.isEmpty() && allowEmptyInput) return true
        return value.length <= maxCharacterCount
    }
}
//endregion

//region Password Text Field Validator
class PasswordTextFieldValidator(
    override var maxCharacterCount: Int = 30,
    override var allowEmptyInput: Boolean = false,
    override var formatter: ((String) -> String)? = null
) : TextValidator {
    override fun restrictCharacters(value: String): String =
        value.filter { it.isLetterOrDigit() || it in "!@#$%^&*()-_=+{}|?>.<,:;~`’" }

    override fun validate(value: String): Boolean {
        if (value.isEmpty() && allowEmptyInput) return true
        val passwordRegex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z!@#$%^&*()\\-_=+{}|?>.<,:;~`’]{8,30}$"
        return value.matches(passwordRegex.toRegex()) && value.length <= maxCharacterCount
    }
}
//endregion

//region Web Link Text Field Validator
class WebLinkTextFieldValidator(
    override var maxCharacterCount: Int = 250,
    override var allowEmptyInput: Boolean = false,
    override var formatter: ((String) -> String)? = TextValidator.webLinkFormatter
) : TextValidator {
    override fun restrictCharacters(value: String): String =
        value.filter { it.isLetterOrDigit() || it in ":/?=&._%-" }

    override fun validate(value: String): Boolean {
        if (value.isEmpty() && allowEmptyInput) return true
        val regexPattern = "^(http://www\\.|https://www\\.|http://|https://)[a-z0-9]+([\\-\\.]{1}[a-z0-9]+)*\\.[a-z]{2,5}(:[0-9]{1,5})?(/.*)?$"
        return value.matches(regexPattern.toRegex()) && value.length <= maxCharacterCount
    }
}
//endregion


