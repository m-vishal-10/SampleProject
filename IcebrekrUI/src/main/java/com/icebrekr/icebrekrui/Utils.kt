package com.icebrekr.utilities.validator

import java.util.regex.Pattern

//region Removing Prefix
fun String.removingPrefix(prefix: String): String {
    return if (this.startsWith(prefix)) {
        this.substring(prefix.length)
    } else {
        this
    }
}
//endregion

//region Filtering Functions
fun String.filteringPhoneNumber(): String {
    // Define the pattern to capture phone numbers including international formats
    val phoneNumberPattern = "[0-9+()\\-\\s]+"
    val regex = Pattern.compile(phoneNumberPattern)
    val matcher = regex.matcher(this)
    val matches = mutableListOf<String>()

    while (matcher.find()) {
        matches.add(matcher.group())
    }

    // Combine all matches into one string
    val combinedMatches = matches.joinToString(separator = "")
    // Remove extra spaces and filter out anything that doesn't look like a valid phone number
    val filteredString = combinedMatches
        .split("\\s+".toRegex())
        .filter { it.isNotEmpty() }
        .joinToString(separator = " ")
    // Find the most valid phone number based on common phone number length
    val validPhoneNumbers = filteredString
        .split("\n".toRegex())
        .filter { phoneNumberContainsAtLeastSevenDigits(it) } // Filtering by a reasonable phone number length
    return validPhoneNumbers.joinToString(separator = " ")
}

private fun phoneNumberContainsAtLeastSevenDigits(phoneNumber: String): Boolean {
    val digits = phoneNumber.replace(Regex("\\D"), "")
    return digits.length >= 7
}

fun String.filteringEmail(): String {
    val emailPattern = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"
    val regex = Pattern.compile(emailPattern)
    val matcher = regex.matcher(this)

    return if (matcher.find()) {
        matcher.group().trim()
    } else {
        ""
    }
}

fun String.filteringLink(): String {
    val linkPattern = "((https|http)://)?([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?"
    val regex = Pattern.compile(linkPattern)
    val matcher = regex.matcher(this)

    return if (matcher.find()) {
        matcher.group().trim()
    } else {
        ""
    }
}
//endregion