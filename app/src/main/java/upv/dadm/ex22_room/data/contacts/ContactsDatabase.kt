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

import androidx.room.Database
import androidx.room.RoomDatabase
import upv.dadm.ex22_room.data.model.ContactDto

/**
 * Version 1 of the RoomDatabase for storing information about contacts.
 * Tables are generated using the ContactDto class.
 */
@Database(entities = [ContactDto::class], version = 1)
abstract class ContactsDatabase : RoomDatabase() {

    // Provides access to the DAO for CRUD operations
    abstract fun contactsDao(): ContactsDao
}