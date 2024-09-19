package com.icebrekr.icebrekrui.reusableviews.list.contactsection

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icebrekr.icebrekrui.R
import com.icebrekr.icebrekrui.configurables.FontType
import com.icebrekr.icebrekrui.configurables.IBCustomFonts
import com.icebrekr.icebrekrui.configurables.IBFonts
import com.icebrekr.icebrekrui.configurables.color.IBTheme
import com.icebrekr.icebrekrui.uielements.imageview.ProfileImageView
import com.icebrekr.icebrekrui.uielements.label.PrimaryLabel
import com.icebrekr.uicomponents.components.buttons.ImageButton

@Composable
fun ContactSectionView(
    section: ContactSection,
    selectedList: MutableState<MutableList<String>>,
    onContactClicked: ((String) -> Unit)? = null,
    onLongPress: ((String) -> Unit)? = null,
    onTap: ((String) -> Unit)? = null,
    onContactLongPress: ((String) -> Unit)? = null
) {
    Column(
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        HeaderText(text = section.title)
        Column(
            modifier = Modifier.padding(top = 4.dp, start = 4.dp, end = 4.dp)
        ) {
            section.contacts.forEach { contact ->
                ContactItem(
                    contact = contact,
                    isSelected = selectedList.value.contains(contact.userId),
                    onContactClicked = onContactClicked,
                    onTap = onTap,
                    onLongPress = onLongPress,
                    onContactLongPress = onContactLongPress
                )
                Spacer(Modifier.padding(4.dp))
            }
        }
    }
}

@Composable
fun ContactItem(
    contact: Contact,
    isSelected: Boolean,
    onContactClicked: ((String) -> Unit)? = null,
    onTap: ((String) -> Unit)? = null,
    onLongPress: ((String) -> Unit)? = null,
    onContactLongPress: ((String) -> Unit)? = null
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(width = 1.dp, color = IBTheme.theme.grey3)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp) // Adjust padding as necessary
        ) {

            ProfileImageView(
                userId = contact.userId,
                isChecked = isSelected,
                link = contact.imageLink,
                onTap = onTap,
                onLongPress = onLongPress
            )
            Spacer(modifier = Modifier.width(10.dp))
            Box(
                modifier = Modifier
                    .weight(0.7f)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onTap = {
                                onContactClicked?.invoke(contact.userId)
                            },
                            onLongPress = {
                                onContactLongPress?.invoke(contact.userId)
                            }
                        )
                    }
            ) {
                PrimaryLabel(
                    text = contact.name.trim(),
                    font = IBFonts.appliedFont(IBCustomFonts.bold, FontType.bodyBold),
                    foregroundColor = IBTheme.theme.primary1Default,
                )
            }

            Box(
                modifier = Modifier
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onTap = {
                                onContactClicked?.invoke(contact.userId)
                            },
                            onLongPress = {
                                onContactLongPress?.invoke(contact.userId)
                            }
                        )
                    }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_contact_indication),
                    contentDescription = "",
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

        }
    }
}

@Composable
fun HeaderText(text: String) {
    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 8.dp) ,
    ) {
        PrimaryLabel(
            text = text,
            font = IBFonts.appliedFont(IBCustomFonts.regular, FontType.callout),
            foregroundColor = IBTheme.theme.grey1,
        )
    }
}

@Preview
@Composable
fun PreviewContactSectionView() {
    val sections = mutableListOf(
        ContactSection(
            title = "Recently Added", contacts = listOf(
                Contact(
                    userId = "1",
                    name = "John Doe",
                    imageLink = "https://picsum.photos/200",
                ),
                Contact(
                    userId = "2",
                    name = "Jane Doe Jane Doe Jane Doe Doe Jane Doe",
                    imageLink = "https://picsum.photos/200",
                )
            )
        ),
        ContactSection(
            title = "One Month Ago", contacts = listOf(
                Contact(
                    userId = "1",
                    name = "John Doe",
                    imageLink = "https://picsum.photos/200",
                ),
                Contact(
                    userId = "2",
                    name = "Jane Doe Jane Doe Jane Doe Doe Jane Doe",
                    imageLink = "https://picsum.photos/200",
                )
            )
        )
    )
    val selectedList = remember {
        mutableStateOf(mutableListOf("1"))
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(sections) { section ->
            ContactSectionView(
                section = section,
                selectedList = selectedList,
                onContactClicked = {

                },
                onTap = {

                },
                onLongPress = {

                }
            )
        }
    }
}