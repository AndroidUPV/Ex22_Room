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

package upv.dadm.ex22_room.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import upv.dadm.ex22_room.data.contacts.ContactsDataSource
import upv.dadm.ex22_room.data.contacts.ContactsDataSourceImpl
import upv.dadm.ex22_room.data.contacts.ContactsRepository
import upv.dadm.ex22_room.data.contacts.ContactsRepositoryImpl

/**
 * Hilt module that determines how to provide required dependencies for interfaces.
 */
@Module
// The Hilt annotation @SingletonComponent creates and destroy instances following the lifecycle of the Application
@InstallIn(SingletonComponent::class)
abstract class ContactBinderModule {

    /**
     * Provides an instance of a ContactsDataSource.
     */
    @Binds
    abstract fun bindContactsDataSource(
        contactsDataSourceImpl: ContactsDataSourceImpl
    ): ContactsDataSource

    /**
     * Provides an instance of a ContactsRepository.
     */
    @Binds
    abstract fun bindContactsRepository(
        contactsRepositoryImpl: ContactsRepositoryImpl
    ): ContactsRepository
}