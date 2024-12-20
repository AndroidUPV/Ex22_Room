/*
 * Copyright (c) 2022-2024 Universitat Politècnica de València
 * Authors: David de Andrés and Juan Carlos Ruiz
 *          Fault-Tolerant Systems
 *          Instituto ITACA
 *          Universitat Politècnica de València
 *
 * Distributed under MIT license
 * (See accompanying file LICENSE.txt)
 */

package upv.dadm.ex22_room.data.contacts

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import upv.dadm.ex22_room.data.model.ContactBriefDto
import upv.dadm.ex22_room.data.model.ContactDto

/**
 * DAO interface to access the contacts information in the database.
 */
@Dao
interface ContactsDao {

    /**
     * Retrieves all the contacts DTO from the database as a Flow, in brief format.
     */
    @Query("SELECT id, name, abbreviation, color FROM contacts")
    fun getAllContacts(): Flow<List<ContactBriefDto>>

    /**
     * Gets all the details for the selected contact DTO from the database.
     */
    @Query("SELECT * FROM contacts WHERE id = :id")
    suspend fun getContactById(id: Int): ContactDto

    /**
     * Adds a variable number of contacts DTO to the database.
     * They replace any contact with matching primary key.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContacts(vararg contacts: ContactDto)

    /**
     * Updates the information of a variable number of contacts DTO in the database.
     */
    @Update
    suspend fun updateContacts(vararg contacts: ContactDto)

    /**
     * Deletes a variable number of contacts DTO from the database.
     */
    @Delete
    suspend fun deleteContacts(vararg contacts: ContactDto)
}