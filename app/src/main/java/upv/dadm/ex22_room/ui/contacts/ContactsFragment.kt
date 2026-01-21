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

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import upv.dadm.ex22_room.R
import upv.dadm.ex22_room.databinding.FragmentContactsBinding
import upv.dadm.ex22_room.ui.contactdetail.ContactDetailViewModel

/**
 * Displays the list of contacts retrieved from the database and
 * a FloatingActionButton to add new contacts to the database
 */
// The Hilt annotation @AndroidEntryPoint is required to receive dependencies from its parent class
@AndroidEntryPoint
class ContactsFragment : Fragment(R.layout.fragment_contacts) {

    // Reference to the ViewModel holding the list of contacts
    private val viewModel: ContactsViewModel by viewModels()

    // Reference to the ViewModel holding the details of the selected contact
    private val detailsViewModel: ContactDetailViewModel by activityViewModels()

    // Backing property to resource binding
    private var _binding: FragmentContactsBinding? = null

    // Property valid between onCreateView() and onDestroyView()
    private val binding
        get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Get the automatically generated view binding for the layout resource
        _binding = FragmentContactsBinding.bind(view)

        // Adapter for the RecyclerView with Vertical LinearLayoutManager
        // Retrieves the details of the selected contact from the database
        val adapter = ContactBriefAdapter(::getSelectedContact)
        binding.recyclerView.adapter = adapter

        // Enter ADDING mode and display the contact's details in a dialog
        binding.fabNewContact.setOnClickListener {
            detailsViewModel.setModeAdding()
            findNavController()
                .currentDestination?.getAction(R.id.navigateToContactDetailsFragment)?.let {
                    findNavController().navigate(R.id.navigateToContactDetailsFragment)
                }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Submit a new list to be displayed whenever the contacts in the database change
                viewModel.contacts.collect { list ->
                    adapter.submitList(list)
                    // Display a message when there are no contacts in the database
                    if (list.isEmpty()) {
                        binding.tvNoContacts.visibility = View.VISIBLE
                        binding.tvNoContacts.text = getString(R.string.no_contacts)
                    } else binding.tvNoContacts.visibility = View.INVISIBLE
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Enter VIEWING mode and display a dialog with the contact's details
                // whenever a contact is selected
                detailsViewModel.selectedContact.collect { contact ->
                    if (contact != null) {
                        detailsViewModel.setModeViewing()
                        findNavController().currentDestination?.getAction(R.id.navigateToContactDetailsFragment)
                            ?.let {
                                findNavController().navigate(R.id.navigateToContactDetailsFragment)
                            }
                    }
                }
            }
        }

    }

    /**
     * Get the selected contact's details from the database and hold them in the ViewModel.
     */
    private fun getSelectedContact(position: Int) =
        detailsViewModel.getContactDetails(viewModel.contacts.value[position].id)


    override fun onDestroyView() {
        super.onDestroyView()
        // Clear resources to make them eligible for garbage collection
        _binding = null
    }
}