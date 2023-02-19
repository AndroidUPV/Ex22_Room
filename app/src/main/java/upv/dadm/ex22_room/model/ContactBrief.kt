/*
 * Copyright (c) 2022
 * David de Andrés and Juan Carlos Ruiz
 * Development of apps for mobile devices
 * Universitat Politècnica de València
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
