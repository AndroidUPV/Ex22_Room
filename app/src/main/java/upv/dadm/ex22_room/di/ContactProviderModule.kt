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

package upv.dadm.ex22_room.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import upv.dadm.ex22_room.data.contacts.ContactsDao
import upv.dadm.ex22_room.data.contacts.ContactsDatabase
import javax.inject.Singleton

/**
 * Hilt module that determines how to provide required dependencies
 * for third party components and Builders.
 */
@Module
// The Hilt annotation @SingletonComponent creates and destroy instances following the lifecycle of the Application
@InstallIn(SingletonComponent::class)
class ContactProviderModule {

    /**
     * Provides an instance of ContactsDatabase.
     */
    @Provides
    // The Singleton annotation ensures that a single instance of this class will ever be created.
    @Singleton
    fun provideContactsRoomDatabase(@ApplicationContext context: Context): ContactsDatabase =
        Room.databaseBuilder(context, ContactsDatabase::class.java, "contacts_database")
            .build()

    /**
     * Provides an instance of ContactsDao.
     */
    @Provides
    // The Singleton annotation ensures that a single instance of this class will ever be created.
    @Singleton
    fun provideContactsDao(contactsDatabase: ContactsDatabase): ContactsDao =
        contactsDatabase.contactsDao()
}