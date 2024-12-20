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

package upv.dadm.ex22_room.ui.contactdetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import upv.dadm.ex22_room.R
import upv.dadm.ex22_room.databinding.FragmentContactDetailsBinding
import upv.dadm.ex22_room.model.Contact

/**
 * Displays the details of the selected contact in VIEWING mode.
 * Enables the edition of the contact's details in EDITING mode.
 * Enables the insertion of the details of a new contact in ADDING mode.
 * All operations can be cancelled or committed to database.
 */
// The Hilt annotation @AndroidEntryPoint is required to receive dependencies from its parent class
@AndroidEntryPoint
class ContactDetailsFragment : BottomSheetDialogFragment(R.layout.fragment_contact_details) {

    // Reference to the ViewModel
    private val viewModel: ContactDetailViewModel by activityViewModels()

    // Backing property to resource binding
    private var _binding: FragmentContactDetailsBinding? = null

    // Property valid between onCreateView() and onDestroyView()
    private val binding
        get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Get the automatically generated view binding for the layout resource
        _binding = FragmentContactDetailsBinding.bind(view)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.selectedContact.collect { selectedContact ->
                    selectedContact?.let { contact ->
                        // Update the text of all elements in the view
                        bindDetails(contact)
                        // Set the abbreviation color to that provided by the contact
                        viewModel.setAbbreviationColor(contact.color)
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    // Set the background color of the abbreviation when it changes
                    binding.cvDetailsAbbreviation.setCardBackgroundColor(uiState.color)

                    // Update the visibility of the edit Button when it changes
                    binding.bEditContact.visibility = uiState.editVisibility()
                    // Update the visibility of the cancel Button when it changes
                    binding.bCancel.visibility = uiState.cancelVisibility()
                    // Update the visibility of the delete Button when it changes
                    binding.bDeleteContact.visibility = uiState.deleteVisibility()
                    // Update the visibility of the save Button when it changes
                    binding.bSaveContact.visibility = uiState.saveVisibility()
                    // Update the enable property of the EditText fields (editable/not editable) when it changes
                    binding.etDetailsName.isEnabled = uiState.isEditTextEnabled()
                    binding.etDetailsEmail.isEnabled = uiState.isEditTextEnabled()
                    binding.etDetailsPhone.isEnabled = uiState.isEditTextEnabled()
                    binding.cvDetailsAbbreviation.isEnabled = uiState.isEditTextEnabled()
                }
            }
        }
        // Update the abbreviation when the contact's name changes
        binding.etDetailsName.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                // The abbreviation will be empty for an empty name
                if (binding.etDetailsName.text?.isEmpty() == true) {
                    binding.tvDetailsAbbreviation.text = ""
                } else {
                    // Split the name into word using blank spaces as delimiters,
                    // and take the first letter of each word to form an acronym
                    val abbreviation =
                        binding.etDetailsName.text.toString()
                            .split(" ")
                            .joinToString(separator = "") { word ->
                                word.first().toString()
                            }
                    // Take the first two letters of the acronym
                    // or the first one is there are no more letters
                    binding.tvDetailsAbbreviation.text =
                        if (abbreviation.length > 2)
                            abbreviation.subSequence(0, 2)
                        else abbreviation
                }
            }
        }
        // Enter EDITING mode
        binding.bEditContact.setOnClickListener {
            viewModel.setModeEditing()
        }
        // Manage the cancel Button
        binding.bCancel.setOnClickListener {
            if (viewModel.uiState.value.mode == ContactDetailViewModel.Mode.ADDING)
            // Dismiss the dialog if in ADDING mode
                dismiss()
            else {
                // Restore the details displayed with those store in the ViewModel
                // and enter VIEWING mode
                bindDetails(viewModel.selectedContact.value!!)
                viewModel.setModeViewing()
            }
        }
        // Manage the save Button
        binding.bSaveContact.setOnClickListener {
            if (binding.etDetailsName.text?.isEmpty() == true) {
                // Display an error message if the contact's name is empty
                displayMessage(R.string.empty_name)
            } else {
                if (viewModel.uiState.value.mode == ContactDetailViewModel.Mode.ADDING) {
                    // Add a new contact to the database with the information entered
                    // and dismiss the dialog, if in ADDING mode
                    viewModel.addContact(
                        Contact(
                            id = 0, // Autogenerate
                            name = binding.etDetailsName.text.toString(),
                            email = binding.etDetailsEmail.text.toString(),
                            phone = binding.etDetailsPhone.text.toString(),
                            abbreviation = binding.tvDetailsAbbreviation.text.toString(),
                            color = viewModel.uiState.value.color
                        )
                    )
                    dismiss()
                } else {
                    // Update the contact in the database with the newly entered information
                    // if in EDITING mode
                    viewModel.updateContact(
                        Contact(
                            id = viewModel.selectedContact.value?.id ?: 0, // Autogenerate
                            name = binding.etDetailsName.text.toString(),
                            email = binding.etDetailsEmail.text.toString(),
                            phone = binding.etDetailsPhone.text.toString(),
                            abbreviation = binding.tvDetailsAbbreviation.text.toString(),
                            color = viewModel.uiState.value.color
                        )
                    )
                }
                // Enter VIEWING mode
                viewModel.setModeViewing()
            }
        }
        // Delete the contact from the database and dismiss the dialog
        binding.bDeleteContact.setOnClickListener {
            viewModel.deleteContact()
            dismiss()
        }
        // Generate a new random color for the abbreviation background
        binding.cvDetailsAbbreviation.setOnClickListener {
            viewModel.generateRandomColor()
        }
        // Send an email to the contact using a the provided details
        binding.tilDetailsEmail.setEndIconOnClickListener {
            if (binding.etDetailsEmail.text?.isNotEmpty() == true)
                try {
                    // Define and launch the implicit Intent to send an email to the contact
                    startActivity(
                        Intent()
                            .setAction(Intent.ACTION_SENDTO)
                            .putExtra(Intent.EXTRA_EMAIL, binding.etDetailsEmail.text)
                    )
                } catch (e: Exception) {
                    // Display an error message if it was not possible to send the email
                    displayMessage(R.string.email_error)
                }

        }
        // Make a phone call to the contact using the provided phone number
        binding.tilDetailsPhone.setEndIconOnClickListener {
            if (binding.etDetailsPhone.text?.isNotEmpty() == true)
                try {
                    // Define and launch the implicit Intent to send an email to the contact
                    startActivity(
                        Intent()
                            .setAction(Intent.ACTION_DIAL)
                            .setData(Uri.parse("tel:${binding.etDetailsPhone.text}"))
                    )
                } catch (e: Exception) {
                    // Display an error message if it was not possible to make the phone call
                    displayMessage(R.string.call_error)
                }
        }
    }

    private fun displayMessage(stringMessageId: Int) =
        Snackbar.make(binding.root, stringMessageId, Snackbar.LENGTH_SHORT).show()


    // Display the contact's details
    private fun bindDetails(contact: Contact) {
        binding.etDetailsName.setText(contact.name)
        binding.etDetailsEmail.setText(contact.email)
        binding.etDetailsPhone.setText(contact.phone)
        binding.tvDetailsAbbreviation.text = contact.abbreviation
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Clear resources to make them eligible for garbage collection
        _binding = null
    }
}