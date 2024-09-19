package com.icebrekr.icebrekrui.moduleviews.leadgeneration.businesscardreader.review.model

//region BCard Progress State Model
enum class BCardProgressStateModel {
    NAME,
    TITLE,
    COMPANY,
    MOBILE,
    EMAIL,
    LINKS;

    fun incrementStep(): BCardProgressStateModel {
        val incrementValue = this.ordinal + 1
        return values().getOrElse(incrementValue) { LINKS }
    }

    fun decrementStep(): BCardProgressStateModel {
        val decrementValue = this.ordinal - 1
        return values().getOrElse(decrementValue) { NAME }
    }

    val info: BCardProgressInfoModel
        get() = when (this) {
            NAME -> BCardProgressInfoModel(
                title = "Full Name",
                subTitle = "Next: Job Title",
                doneText = "Next"
            )

            TITLE -> BCardProgressInfoModel(
                title = "Job Title",
                subTitle = "Next: Mobile Number",
                doneText = "Next"
            )

            COMPANY -> BCardProgressInfoModel(
                title = "Company",
                subTitle = "Next: Mobile Number",
                doneText = "Next"
            )

            MOBILE -> BCardProgressInfoModel(
                title = "Mobile Number",
                subTitle = "Next: Email",
                doneText = "Next"
            )

            EMAIL -> BCardProgressInfoModel(
                title = "Email",
                subTitle = "Next: Links",
                doneText = "Next"
            )

            LINKS -> BCardProgressInfoModel(
                title = "Links",
                subTitle = null,
                doneText = "Done"
            )
        }

}
//endregion

//region BCard Progress Info Model
data class BCardProgressInfoModel(
    val title: String,
    val subTitle: String?,
    val doneText: String
)
//endregion