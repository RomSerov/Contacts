package com.example.decomposesample.domain.usecases

import com.example.decomposesample.domain.model.Contact
import com.example.decomposesample.domain.repository.Repository
import kotlinx.coroutines.flow.Flow

class AddContactUseCase(
    private val repository: Repository
) {
    operator fun invoke(
        userName: String,
        phone: String
    ) {
        val contact = Contact(
            userName = userName,
            phone = phone
        )
        repository.saveContact(contact = contact)
    }
}