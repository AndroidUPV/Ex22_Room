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

package upv.dadm.ex22_room.model

/**
 * A ContactBrief object consisting of just the contact's identifier, name,
 * abbreviation, and abbreviation's background color.
 */
data class ContactBrief(
    val id: Int,
    val name: String,
    val abbreviation: String,
    val color: Int
)
