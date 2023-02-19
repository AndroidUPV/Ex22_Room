/*
 * Copyright (c) 2022
 * David de Andrés and Juan Carlos Ruiz
 * Development of apps for mobile devices
 * Universitat Politècnica de València
 */

package upv.dadm.ex22_room.data.contacts

import kotlinx.coroutines.flow.Flow
import upv.dadm.ex22_room.data.model.ContactBriefDto
import upv.dadm.ex22_room.data.model.ContactDto
import javax.inject.Inject

/**
 * Data source that provides access to the RoomDatabase to manage the details of contats.
 * It implements the ContactsDataSource interface.
 */
class ContactsDataSourceImpl @Inject constructor(
    private val dao: ContactsDao
) : ContactsDataSource {

    /**
     * Returns the list of contacts DTO, in brief format, as a Flow from the database.
     */
    override fun getContactsBrief(): Flow<List<ContactBriefDto>> =
        dao.getAllContacts()

    /**
     * Returns the selected contact DTO from the database.
     */
    override suspend fun getContact(id: Int): ContactDto =
        dao.getContactById(id)

    /**
     * Adds a contact DTO to the database.
     */
    override suspend fun addContact(contact: ContactDto) =
        dao.insertContacts(contact)

    /**
     * Updates a contact DTO from the database.
     */
    override suspend fun updateContact(contact: ContactDto) =
        dao.updateContacts(contact)

    /**
     * Deletes a contact DTO from the database.
     */
    override suspend fun deleteContact(contact: ContactDto) =
        dao.deleteContacts(contact)
}