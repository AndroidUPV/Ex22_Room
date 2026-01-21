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

package upv.dadm.ex22_room.ui.contacts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
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
    val contacts = contactsRepository.getContactsBrief().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = listOf()
    )
}