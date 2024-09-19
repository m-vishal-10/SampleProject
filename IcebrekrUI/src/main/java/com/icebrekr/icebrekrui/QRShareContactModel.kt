package com.icebrekr.sharedresource.sharedmodels.contact

//
//  Created by Vijay on 31/03/23.
//  Copyright © 2024 Icebrekr. All rights reserved.
//

import android.graphics.Bitmap

//region QRShareContactModel
data class QRShareContactModel(
    val name: String?,
    val phone: String?,
    val email: String?,
    val currentRole: String?,
    val companyName: String?,
    val notes: String?,
    val imageString: String?,
    val link1: String?,
    val link2: String?,
    val link3: String?,
    val link4: String?,
    val link5: String?,
    val link6: String?,
    val contactVisibility: QRShareContactVisibility
) {
    fun getQRText(): String {
        val qrStringBuilder = StringBuilder("BEGIN:VCARD")

        if (contactVisibility.isNameVisible && !name.isNullOrBlank()) {
            qrStringBuilder.append("\nFN:$name")
        }

        if (contactVisibility.isPhoneNumberVisible && !phone.isNullOrBlank()) {
            qrStringBuilder.append("\nTEL:$phone")
        }

        if (contactVisibility.isEmailVisible && !email.isNullOrBlank()) {
            qrStringBuilder.append("\nEMAIL:$email")
        }

        if (contactVisibility.isCurrentRoleVisible && !currentRole.isNullOrBlank()) {
            qrStringBuilder.append("\nTITLE:$currentRole")
        }

        if (contactVisibility.isNotesVisible && !notes.isNullOrBlank()) {
            qrStringBuilder.append("\nNOTE:$notes")
        }

        if (contactVisibility.isCompanyNameVisible && !companyName.isNullOrBlank()) {
            qrStringBuilder.append("\nORG:$companyName")
        }

        listOf(
            link1 to contactVisibility.link1Visible,
            link2 to contactVisibility.link2Visible,
            link3 to contactVisibility.link3Visible,
            link4 to contactVisibility.link4Visible,
            link5 to contactVisibility.link5Visible,
            link6 to contactVisibility.link6Visible
        ).forEach { (link, isVisible) ->
            if (isVisible && !link.isNullOrBlank()) {
                qrStringBuilder.append("\nURL:$link")
            }
        }

        if (qrStringBuilder.toString() == "BEGIN:VCARD") {
            return qrStringBuilder.toString()
        }

        imageString?.let {
            qrStringBuilder.append("\nPHOTO;ENCODING=BASE64;TYPE=PNG:$it")
        }

        qrStringBuilder.append("\nEND:VCARD")
        return qrStringBuilder.toString()
    }
}

data class QRShareContactVisibility(
    val isNameVisible: Boolean,
    val isPhoneNumberVisible: Boolean,
    val isEmailVisible: Boolean,
    val isCurrentRoleVisible: Boolean,
    val isCompanyNameVisible: Boolean,
    val isNotesVisible: Boolean,
    val link1Visible: Boolean,
    val link2Visible: Boolean,
    val link3Visible: Boolean,
    val link4Visible: Boolean,
    val link5Visible: Boolean,
    val link6Visible: Boolean
)

data class QRVirtualShareContact(
    val name: String,
    val qrImage: Bitmap,
    var backgroundImageUrl: String?
)
//endregion