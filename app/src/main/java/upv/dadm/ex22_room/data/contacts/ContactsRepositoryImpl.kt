/*
 * Copyright (c) 2022-2026 Universitat Politècnica de València
 * Authors: David de Andrés and Juan Carlos Ruiz
 *          Fault-Tolerant Systems
 *          Instituto ITACA
 *          Universitat Politècnica de València
 *
 * Distributed under MIT license
 * (See accompanying file LICENSE.txt)
 */

package upv.dadm.ex22_room.data.contacts

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import upv.dadm.ex22_room.data.model.toDomain
import upv.dadm.ex22_room.data.model.toDto
import upv.dadm.ex22_room.model.Contact
import upv.dadm.ex22_room.model.ContactBrief
import javax.inject.Inject

/**
 * Repository for managing contacts from the database.
 * It implements the ContactsRepository interface.
 */
class ContactsRepositoryImpl @Inject constructor(
    private val contactsDataSource: ContactsDataSource
) : ContactsRepository {

    /**
     * Returns the list of contacts, in brief format, as a Flow from the database
     * after mapping them to domain.
     */
    override fun getContactsBrief(): Flow<List<ContactBrief>> =
        contactsDataSource.getContactsBrief().map { list ->
            list.map { contactBriefDto ->
                contactBriefDto.toDomain()
            }
        }

    /**
     * Returns the selected contact from the database after mapping it to domain.
     */
    override suspend fun getContact(id: Int): Contact =
        contactsDataSource.getContact(id).toDomain()

    /**
     * Adds a contact to the database after mapping it to DTO.
     */
    override suspend fun addContact(contact: Contact) =
        contactsDataSource.addContact(contact.toDto())

    /**
     * Updates a contact from the database after mapping it to DTO.
     */
    override suspend fun updateContact(contact: Contact) =
        contactsDataSource.updateContact(contact.toDto())

    /**
     * Deletes a contact from the database after mapping it to DTO.
     */
    override suspend fun deleteContact(contact: Contact) =
        contactsDataSource.deleteContact(contact.toDto())
}