/*
 * Copyright (c) 2022-2023 Universitat Politècnica de València
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
import upv.dadm.ex22_room.model.Contact
import upv.dadm.ex22_room.model.ContactBrief

/**
 * Interface declaring the methods that the Repository exposes to ViewModels.
 */
interface ContactsRepository {

    /**
     * Returns the list of contacts, in brief format, as a Flow from the database.
     */
    fun getContactsBrief(): Flow<List<ContactBrief>>

    /**
     * Returns the selected contact from the database.
     */
    suspend fun getContact(id: Int): Contact

    /**
     * Adds a contact to the database.
     */
    suspend fun addContact(contact: Contact)

    /**
     * Updates a contact from the database.
     */
    suspend fun updateContact(contact: Contact)

    /**
     * Deletes a contact from the database.
     */
    suspend fun deleteContact(contact: Contact)
}