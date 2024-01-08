package com.example.decomposesample.domain.repository

import com.example.decomposesample.domain.model.Contact
import kotlinx.coroutines.flow.Flow

interface Repository {

    val contacts: Flow<List<Contact>>

    fun saveContact(contact: Contact)
}