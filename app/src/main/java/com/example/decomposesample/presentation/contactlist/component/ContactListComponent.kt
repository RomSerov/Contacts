package com.example.decomposesample.presentation.contactlist.component

import com.example.decomposesample.domain.model.Contact
import com.example.decomposesample.presentation.contactlist.store.ContactListStore
import kotlinx.coroutines.flow.StateFlow

interface ContactListComponent {

    val model: StateFlow<ContactListStore.State>

    fun onContactClicked(contact: Contact)

    fun onAddContactClicked()
}