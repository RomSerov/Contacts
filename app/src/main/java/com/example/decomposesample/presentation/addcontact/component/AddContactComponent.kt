package com.example.decomposesample.presentation.addcontact.component

import com.example.decomposesample.presentation.addcontact.store.AddContactStore
import kotlinx.coroutines.flow.StateFlow

interface AddContactComponent {

    val model: StateFlow<AddContactStore.State>

    fun onUsernameChange(username: String)

    fun onPhoneChange(phone: String)

    fun onSaveContactClicked()
}