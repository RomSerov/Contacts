package com.example.decomposesample.domain.usecases

import com.example.decomposesample.domain.model.Contact
import com.example.decomposesample.domain.repository.Repository
import kotlinx.coroutines.flow.Flow

class GetContactUseCase(
    private val repository: Repository
) {
    operator fun invoke(): Flow<List<Contact>> = repository.contacts
}