package com.example.decomposesample.domain.usecases

import com.example.decomposesample.domain.model.Contact
import com.example.decomposesample.domain.repository.Repository
import kotlinx.coroutines.flow.Flow

class EditContactUseCase(
    private val repository: Repository
) {
    operator fun invoke(contact: Contact) {
        repository.saveContact(contact = contact)
    }
}