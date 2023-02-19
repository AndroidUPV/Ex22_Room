/*
 * Copyright (c) 2022
 * David de Andrés and Juan Carlos Ruiz
 * Development of apps for mobile devices
 * Universitat Politècnica de València
 */

package upv.dadm.ex22_room.ui.contacts

import android.graphics.Color
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import upv.dadm.ex22_room.data.contacts.ContactsRepository
import upv.dadm.ex22_room.model.Contact
import javax.inject.Inject

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

    // Backing property for the selected contact (default empty, can be null)
    private val _selectedContact = MutableLiveData<Contact?>()

    // Selected contact
    val selectedContact: LiveData<Contact?>
        get() = _selectedContact

    // Backing property for the abbreviation background color (default empty)
    private val _color = MutableLiveData<Int>()

    // Abbreviation background color
    val color: LiveData<Int>
        get() = _color

    // Backing property for the selected operation mode (default empty)
    private val _mode = MutableLiveData<Mode>()

    // Selected operation mode
    val mode: LiveData<Mode>
        get() = _mode

    // Show the edit Button only in VIEWING mode
    val isEditVisible = Transformations.map(mode) { mode ->
        mode == Mode.VIEWING
    }

    // Show the save Button in ADDING and EDITING mode
    val isSaveVisible = Transformations.map(mode) { mode ->
        mode == Mode.ADDING || mode == Mode.EDITING
    }

    // Show the delete Button in VIEWING mode
    val isDeleteVisible = Transformations.map(mode) { mode ->
        mode == Mode.VIEWING
    }

    // Show the cancel Button in ADDING and EDITING mode
    val isCancelVisible = Transformations.map(mode) { mode ->
        mode == Mode.ADDING || mode == Mode.EDITING
    }

    // Enable the EditText fields in ADDING and EDITING mode
    val isEditTextEnabled = Transformations.map(mode) { mode ->
        mode == Mode.ADDING || mode == Mode.EDITING
    }

    /**
     * Sets the selected operation mode
     */
    private fun setMode(mode: Mode) {
        _mode.value = mode
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
            _selectedContact.value =
                contactsRepository.getContact(id)
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
        _selectedContact.value = null
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
        _color.value = newColor
    }

    /**
     * Sets a randomly generated color as the abbreviation's background.
     */
    fun generateRandomColor() {
        setAbbreviationColor(getRandomColor())
    }
}