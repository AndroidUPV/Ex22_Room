/*
 * Copyright (c) 2022
 * David de Andrés and Juan Carlos Ruiz
 * Development of apps for mobile devices
 * Universitat Politècnica de València
 */

package upv.dadm.ex22_room.ui.contacts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import dagger.hilt.android.lifecycle.HiltViewModel
import upv.dadm.ex22_room.data.contacts.ContactsRepository
import javax.inject.Inject

/**
 * Holds the list of contacts retrieved from the database.
 */
// The Hilt annotation @HiltEntryPoint is required to receive dependencies from its parent class
@HiltViewModel
class ContactsViewModel @Inject constructor(
    contactsRepository: ContactsRepository
) : ViewModel() {

    // List of contacts retrieved from the database as a Flow and changed into LiveData
    val contacts = contactsRepository.getContactsBrief().asLiveData()

    // Display a message whenever there are no contacts in the database
    val isMessageVisible = contacts.map { list ->
        list.isEmpty()
    }

}