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

package upv.dadm.ex22_room.ui.contacts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import upv.dadm.ex22_room.databinding.ItemContactBinding
import upv.dadm.ex22_room.model.ContactBrief

class ContactBriefAdapter(private val onItemClicked: (Int) -> Unit) :
    ListAdapter<ContactBrief, ContactBriefAdapter.ViewHolder>(ContactCallback) {

    /**
     * Computes the diff between two contacts in the array.
     */
    object ContactCallback : DiffUtil.ItemCallback<ContactBrief>() {
        /**
         * Determines whether two contacts are the same (let's use their identifier).
         */
        override fun areItemsTheSame(oldItem: ContactBrief, newItem: ContactBrief): Boolean =
            oldItem.id == newItem.id

        /**
         * Determines whether two contacts have the same data.
         */
        override fun areContentsTheSame(oldItem: ContactBrief, newItem: ContactBrief): Boolean =
            oldItem == newItem

    }

    /**
     * Holds a reference to a ViewBinding and
     * sets a listener to react to any click on the View.
     */
    class ViewHolder(
        private val binding: ItemContactBinding,
        private val onItemClicked: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            // This listener will be executed when the View is clicked
            // to display the details of the contact clicked
            binding.root.setOnClickListener { onItemClicked(adapterPosition) }
        }

        /**
         * Fills the elements within the View with the provided ContactBrief object.
         */
        fun bind(contact: ContactBrief) {
            binding.tvContactName.text = contact.name

            binding.tvContactAcronym.text = contact.abbreviation
            binding.tvContactCard.setCardBackgroundColor(contact.color)
        }
    }

    /**
     * Creates the ViewBinding and attaches it to a ViewHolder
     * to easily access all the elements within the View.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemContactBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onItemClicked
        )

    /**
     * Fills the elements within the View with the required data from the array.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))
}