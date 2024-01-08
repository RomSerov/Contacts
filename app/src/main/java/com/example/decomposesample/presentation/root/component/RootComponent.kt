package com.example.decomposesample.presentation.root.component

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.example.decomposesample.presentation.addcontact.component.AddContactComponent
import com.example.decomposesample.presentation.contactlist.component.ContactListComponent
import com.example.decomposesample.presentation.editcontact.component.EditContactComponent

interface RootComponent {

    val stack: Value<ChildStack<*, Child>>

    sealed interface Child {

        class AddContact(val component: AddContactComponent): Child

        class ContactList(val component: ContactListComponent): Child

        class EditContact(val component: EditContactComponent): Child
    }
}