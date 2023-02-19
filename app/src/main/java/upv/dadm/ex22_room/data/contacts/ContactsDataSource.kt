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

/**
 * Interface declaring the methods that the DataSource exposes to Repositories.
 */
interface ContactsDataSource {

    /**
     * Returns the list of contacts DTO, in brief format, as a Flow from the database.
     */
    fun getContactsBrief(): Flow<List<ContactBriefDto>>

    /**
     * Returns the selected contact DTO from the database.
     */
    suspend fun getContact(id: Int): ContactDto

    /**
     * Adds a contact DTO to the database.
     */
    suspend fun addContact(contact: ContactDto)

    /**
     * Updates a contact DTO from the database.
     */
    suspend fun updateContact(contact: ContactDto)

    /**
     * Deletes a contact DTO from the database.
     */
    suspend fun deleteContact(contact: ContactDto)
}