package com.example.decomposesample.presentation.addcontact.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.example.decomposesample.core.componentScope
import com.example.decomposesample.presentation.addcontact.store.AddContactStore
import com.example.decomposesample.presentation.addcontact.store.AddContactStoreFactory
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DefaultAddContactComponent(
    componentContext: ComponentContext,
    private val onContactSaved: () -> Unit
) : AddContactComponent, ComponentContext by componentContext {

    private val store: AddContactStore = instanceKeeper.getStore {
        val storeFactory = AddContactStoreFactory()
        storeFactory.create()
    }

    init {
        componentScope().launch {
            store.labels.collect {
                when(it) {
                    AddContactStore.Label.ContactSaved -> {
                        onContactSaved()
                    }
                }
            }
        }
    }

    override val model: StateFlow<AddContactStore.State>
        get() = store.stateFlow

    override fun onUsernameChange(username: String) {
        store.accept(AddContactStore.Intent.ChangeUsername(username = username))
    }

    override fun onPhoneChange(phone: String) {
        store.accept(AddContactStore.Intent.ChangePhone(phone = phone))
    }

    override fun onSaveContactClicked() {
        store.accept(AddContactStore.Intent.SaveContact)
    }
}