package com.example.decomposesample.data

import com.example.decomposesample.domain.model.Contact
import com.example.decomposesample.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow

object RepositoryImpl: Repository {

    private val _contactList = mutableMapOf(
        0 to Contact(
            id = 0,
            userName = "Ivan Ivanov",
            phone = "+7-999-999-00-01"
        ),
        1 to Contact(
            id = 1,
            userName = "Petr Petrov",
            phone = "+7-999-999-00-02"
        ),
        2 to Contact(
            id = 2,
            userName = "Sidr Sidorov",
            phone = "+7-999-999-00-03"
        ),
    )

    private val contactList: List<Contact>
        get() = _contactList.values.toList()

    private val contactListChangeEvent = MutableSharedFlow<Unit>(replay = 1).apply {
        tryEmit(Unit)
    }

    override val contacts: Flow<List<Contact>> = flow {
        contactListChangeEvent.collect {
            emit(contactList)
        }
    }

    override fun saveContact(contact: Contact) {
        val id = if (contact.id < 0) contactList.size else contact.id
        _contactList[id] = contact.copy(id = id)
        contactListChangeEvent.tryEmit(Unit)
    }
}