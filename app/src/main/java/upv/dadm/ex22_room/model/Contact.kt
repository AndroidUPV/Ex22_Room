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

package upv.dadm.ex22_room.model

/**
 * A contact object consisting in its identifier, name, email address, phone number,
 * abbreviation, and abbreviation's background color.
 */
data class Contact(
    val id: Int,
    val name: String,
    val email: String,
    val phone: String,
    val abbreviation: String,
    val color: Int
)
