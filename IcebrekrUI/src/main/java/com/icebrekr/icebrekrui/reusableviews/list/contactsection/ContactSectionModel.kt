package com.icebrekr.icebrekrui.reusableviews.list.contactsection

import java.util.*

//MARK: - Section Model
data class ContactSection(
    val id: UUID = UUID.randomUUID(),
    val title: String,
    var contacts: List<Contact>
)

//MARK: - Contact Model
data class Contact(
    val userId: String,
    var name: String,
    var imageLink: String?
)
