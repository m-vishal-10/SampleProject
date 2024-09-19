package com.icebrekr.icebrekrui.tryouts.leadgeneration

import NavigationHeaderView
import PrimarySearchBar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.icebrekr.icebrekrui.configurables.FontType
import com.icebrekr.icebrekrui.configurables.IBCustomFonts
import com.icebrekr.icebrekrui.configurables.IBFonts
import com.icebrekr.icebrekrui.configurables.color.IBTheme
import com.icebrekr.icebrekrui.helper.PackageImage
import com.icebrekr.icebrekrui.reusableviews.actionviews.ContactActionState
import com.icebrekr.icebrekrui.reusableviews.actionviews.ContactActionView
import com.icebrekr.icebrekrui.reusableviews.list.contactsection.Contact
import com.icebrekr.icebrekrui.reusableviews.list.contactsection.ContactSection
import com.icebrekr.icebrekrui.reusableviews.list.contactsection.ContactSectionView
import com.icebrekr.icebrekrui.uielements.label.PrimaryLabel
import com.icebrekr.uicomponents.components.buttons.ImageButton

//region Contact List Action
sealed class ContactListAction {
    object Refresh : ContactListAction()
    object Back : ContactListAction()
    object AddContact : ContactListAction()
    data class Delete(val contactIds: List<String>) : ContactListAction()
    data class Export(val contactIds: List<String>) : ContactListAction()
    data class ContactClicked(val contactId: String) : ContactListAction()
}
//endregion

//region Contact List View
@Composable
fun ContactListView(
    searchText: MutableState<String>,
    sections: List<ContactSection>,
    selectionList: MutableState<MutableList<String>>,
    isSelected: MutableState<Boolean>,
    isRefreshing: MutableState<Boolean>,
    action: ((ContactListAction) -> Unit)? = null,
) {

    //Variables
    val isOverflowMenuOn = remember { mutableStateOf(false) }
    isOverflowMenuOn.value = selectionList.value.isNotEmpty()

    isSelected.value = selectionList.value.isNotEmpty()

    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isRefreshing.value)

    Surface(
        color = IBTheme.theme.white,
        modifier = Modifier.fillMaxSize()
    ) {
        Column {

            //region Contact List - Header
            NavigationHeaderView(
                titleText = "Contacts",
                backActionIcon = PackageImage.closeBlue.toImage(),
                primaryActionIcon = PackageImage.add.toImage()
            ) { it ->
                when (it) {
                    NavigationAction.backAction -> {
                        action?.invoke(ContactListAction.Back)
                    }
                    NavigationAction.primaryAction -> {
                        action?.invoke(ContactListAction.AddContact)
                    }

                    else -> {}
                }
            }
            //endregion

            //region Contact List - Search
            PrimarySearchBar(
                onTextChange = { newText ->
                    searchText.value = newText
                },
            )
            //endregion

            //region Contact List Overlay Menu
            OverlayMenuView(isOverflowMenuOn, selectionList)
            //region

            //region Contact List Action
            if (isOverflowMenuOn.value) {
                ContactActionView(isSelected = isSelected) {
                    when (it) {
                        ContactActionState.SelectAll -> {
                            selectionList.value =
                                selectionList.value.toMutableList().apply { clear() }
                            sections.forEach { contactSection ->
                                contactSection.contacts.forEach { contact ->
                                    selectionList.value = selectionList.value.toMutableList()
                                        .apply { add(contact.userId) }
                                }
                            }
                            isSelected.value = true
                        }

                        ContactActionState.UnSelectAll -> {
                            selectionList.value =
                                selectionList.value.toMutableList().apply { clear() }
                            isOverflowMenuOn.value = false
                        }

                        ContactActionState.Delete -> {
                            action?.invoke(ContactListAction.Delete(selectionList.value))
                        }

                        ContactActionState.Export -> {
                            action?.invoke(ContactListAction.Export(selectionList.value))
                            selectionList.value =
                                selectionList.value.toMutableList().apply { clear() }
                        }
                    }
                }
            }
            //endregion

            if (sections.isNotEmpty()) {
                GenerateContactSection(
                    sections = sections,
                    selectionList = selectionList,
                    swipeRefreshState = swipeRefreshState,
                    onContactClicked = { id ->
                        action?.invoke(ContactListAction.ContactClicked(id))
                    },
                    onTap = { id ->

                        if ((isOverflowMenuOn.value || selectionList.value.isNotEmpty())) {

                            if (selectionList.value.contains(id)) {
                                selectionList.value =
                                    selectionList.value.toMutableList().apply { remove(id) }
                                if (selectionList.value.isEmpty()) {
                                    isSelected.value = false
                                }
                            } else {
                                selectionList.value =
                                    selectionList.value.toMutableList().apply { add(id) }
                            }

                        }

                    },
                    onLongPress = { id ->

                        isOverflowMenuOn.value = true
                        if (selectionList.value.contains(id)) {
                            selectionList.value =
                                selectionList.value.toMutableList().apply { remove(id) }
                            if (selectionList.value.isEmpty()) {
                                isSelected.value = false
                            }
                        } else {
                            selectionList.value =
                                selectionList.value.toMutableList().apply { add(id) }
                        }

                    }
                ) {
                    action?.invoke(ContactListAction.Refresh)
                }
            } else {
                NoContactsView(
                    action = action,
                    swipeRefreshState = swipeRefreshState
                )
            }
        }
    }

}
//endregion

//region Contact List - Section
@Composable
fun GenerateContactSection(
    sections: List<ContactSection>,
    selectionList: MutableState<MutableList<String>>,
    swipeRefreshState: SwipeRefreshState,
    onContactClicked: ((String) -> Unit),
    onTap: ((String) -> Unit),
    onLongPress: ((String) -> Unit),
    onRefresh: (() -> Unit)
) {
    SwipeRefresh(state = swipeRefreshState, onRefresh = {
        onRefresh.invoke()
    }) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(sections) { section ->
                ContactSectionView(
                    section = section,
                    selectedList = selectionList,
                    onContactClicked = { id -> onContactClicked(id) },
                    onTap = { id -> onTap(id) },
                    onLongPress = { id -> onLongPress(id) },
                    onContactLongPress = {id -> onLongPress(id)}
                )
            }
        }
    }
}
//endregion

//region Contact List Overlay View
@Composable
private fun OverlayMenuView(isOverflowMenuOn: MutableState<Boolean>, selectionList: MutableState<MutableList<String>>) {

    Row(
        modifier = Modifier
            .padding(end = 20.dp, bottom = 4.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ImageButton(
            image = PackageImage.overflow.toImage(),
            foregroundColor = IBTheme.theme.primary2Disabled,
            onTap = {
                if (selectionList.value.isNotEmpty()) {
                    isOverflowMenuOn.value = true
                    return@ImageButton
                }
                isOverflowMenuOn.value = !isOverflowMenuOn.value
            }
        )
    }
}
//endregion

//region Contact List No contacts View
@Composable
fun NoContactsView(
    action: ((ContactListAction) -> Unit)?,
    swipeRefreshState: SwipeRefreshState
) {
    SwipeRefresh(state = swipeRefreshState, onRefresh = {
        action?.invoke(ContactListAction.Refresh)
    }) {
        Box(
            modifier = Modifier
                .height(200.dp)
                .padding(horizontal = 30.dp),
            contentAlignment = Alignment.Center,
        ) {
            PrimaryLabel(
                text = "No contacts scanned yet.\nPlease use scan feature to scan the contact",
                font = IBFonts.appliedFont(IBCustomFonts.regular, FontType.callout),
                foregroundColor = IBTheme.theme.grey1,
                textAlign = TextAlign.Center
            )
        }
    }
}
//endregion

private fun isAllContactsSelected(
    sections: List<ContactSection>,
    selectionList: List<String>
): Boolean {
    val contacts = sections.flatMap { it.contacts }
    return contacts.size == selectionList.size
}

//region Contact List - Preview
@Preview
@Composable
fun ContactViewPreview() {

    val sections =
        listOf(
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
    val longPressClicked = remember {
        mutableStateOf(false)
    }

    val searchTerm = remember {
        mutableStateOf("")
    }

    val isRefreshing = remember {
        mutableStateOf(false)
    }

    ContactListView(
        searchText = searchTerm,
        sections = sections,
        selectionList = selectedList,
        isSelected = longPressClicked,
        isRefreshing = isRefreshing
    ) {

    }
}
//endregion