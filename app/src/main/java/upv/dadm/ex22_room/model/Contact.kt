/*
 * Copyright (c) 2022
 * David de Andrés and Juan Carlos Ruiz
 * Development of apps for mobile devices
 * Universitat Politècnica de València
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
