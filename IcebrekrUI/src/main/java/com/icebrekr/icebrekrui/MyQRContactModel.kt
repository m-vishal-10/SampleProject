package com.icebrekr.sharedresource.sharedmodels.contact

//
//  MyQRContactModel.kt
//
//
//  Created by Vijay on 02/07/24.
//

import android.graphics.Bitmap

//region My QR Contact Model
data class MyQRContactModel(
    val profileImageUrl: String,
    val qrCodeImage: Bitmap?,
    val name: String,
    val title: String,
    val urlString: String?
) {
    companion object {
        fun initial(): MyQRContactModel {
            return MyQRContactModel(
                profileImageUrl = "https://images.unsplash.com/photo-1719702702556-43c793e2b625?q=80&w=3028&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                qrCodeImage = null,
                name = "",
                title = "",
                urlString = null
            )
        }

        fun mock(): MyQRContactModel {
            return MyQRContactModel(
                profileImageUrl = "https://images.unsplash.com/photo-1719702702556-43c793e2b625?q=80&w=3028&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                qrCodeImage = null, // Update this with the correct method to get a QR code image
                name = "John Doe",
                title = "Software Engineer",
                urlString = "https://images.unsplash.com/photo-1719702702556-43c793e2b625?q=80&w=3028&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
            )
        }
    }
}
//endregion