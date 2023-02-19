/*
 * Copyright (c) 2022
 * David de Andrés and Juan Carlos Ruiz
 * Development of apps for mobile devices
 * Universitat Politècnica de València
 */

package upv.dadm.ex22_room.data.model

import androidx.room.ColumnInfo

/**
 * A ContactBrief DTO object consisting of a contact's identifier, name,
 * abbreviation, and abbreviation's background color.
 */
// Properties are mapped to database columns to be able to get this subset when querying the database
data class ContactBriefDto(
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "abbreviation")
    val abbreviation: String,
    @ColumnInfo(name = "color")
    val color: Int
)
