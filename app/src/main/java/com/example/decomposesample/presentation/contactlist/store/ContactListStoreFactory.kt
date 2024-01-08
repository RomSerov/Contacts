package com.example.decomposesample.presentation.contactlist.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.example.decomposesample.data.RepositoryImpl
import com.example.decomposesample.domain.model.Contact
import com.example.decomposesample.domain.usecases.GetContactUseCase
import kotlinx.coroutines.launch

class ContactListStoreFactory {

    private val storeFactory: StoreFactory = DefaultStoreFactory()
    private val repository = RepositoryImpl
    private val getContactUseCase = GetContactUseCase(repository = repository)

    fun create(): ContactListStore = object : ContactListStore,
        Store<ContactListStore.Intent, ContactListStore.State, ContactListStore.Label> by storeFactory.create(
            name = "ContactListStore",
            initialState = ContactListStore.State(contactList = listOf()),
            bootstrapper = BootstrapperImpl(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {
        data class ContactsLoaded(val contacts: List<Contact>) : Action
    }

    private sealed interface Msg {
        data class ContactsLoaded(val contacts: List<Contact>) : Msg
    }

    private inner class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch {
                getContactUseCase().collect {
                    dispatch(Action.ContactsLoaded(contacts = it))
                }
            }
        }
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<ContactListStore.Intent, Action, ContactListStore.State, Msg, ContactListStore.Label>() {

        override fun executeAction(action: Action, getState: () -> ContactListStore.State) {
            when (action) {
                is Action.ContactsLoaded -> {
                    dispatch(Msg.ContactsLoaded(contacts = action.contacts))
                }
            }
        }

        override fun executeIntent(
            intent: ContactListStore.Intent,
            getState: () -> ContactListStore.State
        ) {
            when (intent) {
                ContactListStore.Intent.AddContact -> {
                    publish(ContactListStore.Label.AddContact)
                }

                is ContactListStore.Intent.SelectContact -> {
                    publish(ContactListStore.Label.EditContact(contact = intent.contact))
                }
            }
        }
    }

    private object ReducerImpl : Reducer<ContactListStore.State, Msg> {
        override fun ContactListStore.State.reduce(msg: Msg): ContactListStore.State = when (msg) {
            is Msg.ContactsLoaded -> {
                copy(contactList = msg.contacts)
            }
        }
    }
}