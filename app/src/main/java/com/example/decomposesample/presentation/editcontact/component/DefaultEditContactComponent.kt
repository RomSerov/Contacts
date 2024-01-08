package com.example.decomposesample.presentation.editcontact.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.example.decomposesample.core.componentScope
import com.example.decomposesample.domain.model.Contact
import com.example.decomposesample.presentation.editcontact.store.EditContactStore
import com.example.decomposesample.presentation.editcontact.store.EditContactStoreFactory
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DefaultEditContactComponent(
    componentContext: ComponentContext,
    private val contact: Contact,
    private val onContactSaved: () -> Unit
) : EditContactComponent, ComponentContext by componentContext {

    private val store: EditContactStore = instanceKeeper.getStore {
        val storeFactory = EditContactStoreFactory()
        storeFactory.create(contact = contact)
    }

    init {
        componentScope().launch {
            store.labels.collect {
                when (it) {
                    EditContactStore.Label.ContactSaved -> {
                        onContactSaved()
                    }
                }
            }
        }
    }

    override val model: StateFlow<EditContactStore.State>
        get() = store.stateFlow

    override fun onUsernameChange(username: String) {
        store.accept(EditContactStore.Intent.ChangeUsername(username = username))
    }

    override fun onPhoneChange(phone: String) {
        store.accept(EditContactStore.Intent.ChangePhone(phone = phone))
    }

    override fun onSaveContactClicked() {
        store.accept(EditContactStore.Intent.SaveContact)
    }
}