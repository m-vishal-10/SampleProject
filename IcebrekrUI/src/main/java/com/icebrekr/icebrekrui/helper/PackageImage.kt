package com.icebrekr.icebrekrui.helper

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.icebrekr.icebrekrui.R
import com.icebrekr.icebrekrui.configurables.color.IBTheme
import com.icebrekr.uicomponents.components.buttons.ImageButton

enum class PackageImage {
    close,
    closeDark,
    closeCovered,
    closeBlue,
    info,
    qRCode,
    appIcon,
    notificationBell,
    settingsGear,
    delete,
    export,
    checkMark,
    checkNo,
    checkMarkBlue,
    contactIndication,
    backChevron,
    secureOpen,
    secureClosed,
    overflow,
    addPhoto,
    mail,
    link,
    phone,
    message,
    rightCheck,
    open,
    copy,
    share,
    dropdown,
    qrCodeBlue,
    bCard,
    myQrCard,
    flashOn,
    flashOff,
    capture,
    shareContact,
    add,
    download,
    leftArrow,
    rightArrow,
    leftArrowSecondary,
    rightArrowSecondary;

    fun toImage(): Int {
        val fontRes = when (this) {
            close -> R.drawable.ic_close_button
            closeDark -> R.font.notosans_blackitalic
            closeCovered -> R.drawable.ic_close_covered
            closeBlue -> R.drawable.ic_close_blue
            info -> R.drawable.ic_icon
            qRCode -> R.drawable.ic_qr_code
            appIcon -> R.font.notosans_bold
            notificationBell -> R.font.notosans_extrabold
            settingsGear -> R.font.notosans_extrabolditalic
            delete -> R.drawable.ic_delete
            export -> R.drawable.ic_export
            checkMark -> R.drawable.ic_check_mark
            checkNo -> R.drawable.ic_check_no
            checkMarkBlue -> R.drawable.ic_check_blue
            contactIndication -> R.drawable.ic_contact_indication
            backChevron -> R.drawable.ic_back_arrow
            secureOpen -> R.drawable.ic_contact_indication
            addPhoto -> R.drawable.add_photo
            secureClosed -> R.drawable.ic_close_button
            overflow -> R.drawable.ic_overflow
            mail -> R.drawable.ic_email
            link -> R.drawable.ic_web
            phone -> R.drawable.ic_phone
            message -> R.drawable.img_message
            rightCheck -> R.drawable.ic_right_check
            open -> R.drawable.ic_open
            copy -> R.drawable.ic_copy
            share -> R.drawable.ic_img_share
            dropdown -> R.drawable.ic_drop_down
            qrCodeBlue -> R.drawable.ic_qr_code_blue
            bCard -> R.drawable.ic_bcard
            myQrCard -> R.drawable.ic_my_qr
            flashOn -> R.drawable.ic_flash_on
            flashOff -> R.drawable.ic_flash_off
            capture -> R.drawable.ic_capture
            shareContact -> R.drawable.ic_share_contact
            add -> R.drawable.ic_add
            download -> R.drawable.ic_img_download
            leftArrow -> R.drawable.ic_left_arrow
            rightArrow -> R.drawable.ic_right_arrow
            leftArrowSecondary -> R.drawable.ic_left_arrow_secondary
            rightArrowSecondary -> R.drawable.ic_right_arrow_secondary
        }
        return fontRes
    }
}

@Composable
fun ImageViewer(image: Int) {
    Box(
        modifier = Modifier
            .background(Color.Black)
    ) {
        ImageButton(
            image = image,
            foregroundColor = IBTheme.theme.white,
            dimension = Pair(100f, 100f),
            onTap = {

            }
        )
    }
}

@Preview
@Composable
fun ImageViewerPreview() {
    ImageViewer(PackageImage.overflow.toImage())
}
