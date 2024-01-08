package com.example.decomposesample.presentation.editcontact.component

import com.example.decomposesample.presentation.editcontact.store.EditContactStore
import kotlinx.coroutines.flow.StateFlow

interface EditContactComponent {

    val model: StateFlow<EditContactStore.State>

    fun onUsernameChange(username: String)

    fun onPhoneChange(phone: String)

    fun onSaveContactClicked()
}