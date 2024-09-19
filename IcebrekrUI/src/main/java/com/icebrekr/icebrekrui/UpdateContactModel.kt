package com.icebrekr.sharedresource.sharedmodels.contact

//region UpdateContactModel
data class UpdateContactModel(
    var id: String?,
    var name: String?,
    var primaryMobile: String?,
    var primaryEmail: String?,
    var title: String?,
    var company: String?,
    var note: String?,
    var links: MutableMap<String, String>?,
    var emails: MutableMap<String, String>?,
    var mobiles: MutableMap<String, String>?,
    var photo: String?,
    var lastUpdatedAt: Long?
) {
    constructor() : this(
        id = null,
        name = null,
        primaryMobile = null,
        primaryEmail = null,
        title = null,
        company = null,
        note = null,
        links = null,
        emails = null,
        mobiles = null,
        photo = null,
        lastUpdatedAt = null
    )

    companion object {

        fun initial(): UpdateContactModel {
            return UpdateContactModel()
        }

        fun mock(): UpdateContactModel {
            return UpdateContactModel(
                id = "1",
                name = "Akansha Patil",
                primaryMobile = "+91 22 6718 6718",
                primaryEmail = "Nectorsource@gmail.com",
                title = "Chief Marketing Officer",
                company = "Nectorsource",
                note = "Important contact",
                links = mutableMapOf(
                    "Website" to "www.nectorsorcemfg.Web",
                    "Link" to "www.nectors.com"
                ),
                emails = mutableMapOf("Work" to "work@example.com"),
                mobiles = mutableMapOf("Work" to "+91 22 6718 6718"),
                photo = "profile_photo_url",
                lastUpdatedAt = 1622548800L
            )
        }
    }
}
//endregion