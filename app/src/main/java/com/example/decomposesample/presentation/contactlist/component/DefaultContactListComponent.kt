package com.example.decomposesample.presentation.contactlist.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.example.decomposesample.core.componentScope
import com.example.decomposesample.data.RepositoryImpl
import com.example.decomposesample.domain.model.Contact
import com.example.decomposesample.domain.usecases.GetContactUseCase
import com.example.decomposesample.presentation.contactlist.store.ContactListStore
import com.example.decomposesample.presentation.contactlist.store.ContactListStoreFactory
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DefaultContactListComponent(
    componentContext: ComponentContext,
    val onEditingContactRequested: (Contact) -> Unit,
    val onAddContactRequested: () -> Unit
): ContactListComponent,ComponentContext by componentContext {

    private val store: ContactListStore = instanceKeeper.getStore {
        val storeFactory = ContactListStoreFactory()
        storeFactory.create()
    }

    init {
        componentScope().launch {
            store.labels.collect {
                when(it) {
                    ContactListStore.Label.AddContact -> {
                        onAddContactRequested()
                    }
                    is ContactListStore.Label.EditContact -> {
                        onEditingContactRequested(it.contact)
                    }
                }
            }
        }
    }

    override val model: StateFlow<ContactListStore.State>
        get() = store.stateFlow

    override fun onContactClicked(contact: Contact) {
        store.accept(ContactListStore.Intent.SelectContact(contact = contact))
    }

    override fun onAddContactClicked() {
        store.accept(ContactListStore.Intent.AddContact)
    }
}