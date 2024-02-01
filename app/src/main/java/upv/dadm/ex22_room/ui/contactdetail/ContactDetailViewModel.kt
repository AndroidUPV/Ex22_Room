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

package upv.dadm.ex22_room.ui.contactdetail

import android.graphics.Color
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import upv.dadm.ex22_room.data.contacts.ContactsRepository
import upv.dadm.ex22_room.model.Contact
import javax.inject.Inject

/**
 * A ContactDetailsUiState object containing the background color
 * of the contact's name abbreviation, and the edition mode.
 */
data class ContactDetailsUiState(
    val color: Int,
    val mode: ContactDetailViewModel.Mode
) {
    // Show the edit Button only in VIEWING mode
    fun editVisibility() =
        if (mode == ContactDetailViewModel.Mode.VIEWING) View.VISIBLE else View.INVISIBLE

    // Show the save Button in ADDING and EDITING mode
    fun saveVisibility() =
        if (mode == ContactDetailViewModel.Mode.ADDING || mode == ContactDetailViewModel.Mode.EDITING) View.VISIBLE else View.INVISIBLE

    // Show the delete Button in VIEWING mode
    fun deleteVisibility() =
        if (mode == ContactDetailViewModel.Mode.VIEWING) View.VISIBLE else View.INVISIBLE

    // Show the cancel Button in ADDING and EDITING mode
    fun cancelVisibility() =
        if (mode == ContactDetailViewModel.Mode.ADDING || mode == ContactDetailViewModel.Mode.EDITING) View.VISIBLE else View.INVISIBLE

    // Enable the EditText fields in ADDING and EDITING mode
    fun isEditTextEnabled() =
        mode == ContactDetailViewModel.Mode.ADDING || mode == ContactDetailViewModel.Mode.EDITING
}

/**
 * Holds information about the selected contact.
 */
// The Hilt annotation @HiltEntryPoint is required to receive dependencies from its parent class
@HiltViewModel
class ContactDetailViewModel @Inject constructor(
    private val contactsRepository: ContactsRepository
) : ViewModel() {

    // Enumeration the defines the available operation modes
    enum class Mode { VIEWING, ADDING, EDITING }

    // Backing property for the selected contact
    private val _selectedContact = MutableStateFlow<Contact?>(null)
    // Selected contact
    val selectedContact = _selectedContact.asStateFlow()

    // UI state (mutable): color and mode properties
    private val _uiState =
        MutableStateFlow(ContactDetailsUiState(0, Mode.VIEWING))
    // Backing property (immutable)
    val uiState = _uiState.asStateFlow()

    /**
     * Sets the selected operation mode
     */
    private fun setMode(mode: Mode) {
        _uiState.update { state -> state.copy(mode = mode) }
    }

    /**
     * Sets the operation mode to VIEWING.
     */
    fun setModeViewing() {
        setMode(Mode.VIEWING)
    }

    /**
     * Sets the operation mode to EDITING.
     */
    fun setModeEditing() {
        setMode(Mode.EDITING)
    }

    /**
     * Sets the operation mode to ADDING.
     */
    fun setModeAdding() {
        setMode(Mode.ADDING)
        clearSelectedContact()
    }

    /**
     * Adds a new contact to the database.
     */
    fun addContact(contact: Contact) {
        viewModelScope.launch {
            contactsRepository.addContact(contact)
        }
    }

    /**
     * Gets the details of the selected contact from the database.
     */
    fun getContactDetails(id: Int) {
        viewModelScope.launch {
            _selectedContact.update { contactsRepository.getContact(id) }
        }
    }

    /**
     * Updates the details of the selected contact in the database.
     */
    fun updateContact(contact: Contact) {
        viewModelScope.launch {
            contactsRepository.updateContact(contact)
        }
    }

    /**
     * Deletes the selected contact from the database.
     */
    fun deleteContact() {
        viewModelScope.launch {
            contactsRepository.deleteContact(selectedContact.value!!)
        }
    }

    /**
     * Clears the details of the selected contact
     * to allow for entering the information of a new one,
     * and generate a new background color for the contact's abbreviation.
     */
    private fun clearSelectedContact() {
        _selectedContact.update { null }
        generateRandomColor()
    }

    /**
     * Generates a solid (alpha = #FF) random RGB color.
     */
    private fun getRandomColor(): Int =
        Color.argb(
            255,
            (0..255).random(),
            (0..255).random(),
            (0..255).random()
        )

    /**
     * Sets the background color of the abbreviation.
     */
    fun setAbbreviationColor(newColor: Int) {
        _uiState.update { state -> state.copy(color = newColor) }
    }

    /**
     * Sets a randomly generated color as the abbreviation's background.
     */
    fun generateRandomColor() {
        setAbbreviationColor(getRandomColor())
    }
}