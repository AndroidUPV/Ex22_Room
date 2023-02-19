/*
 * Copyright (c) 2022
 * David de Andrés and Juan Carlos Ruiz
 * Development of apps for mobile devices
 * Universitat Politècnica de València
 */

package upv.dadm.ex22_room.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * A contact DTO object consisting in its identifier, name, email address, phone number,
 * abbreviation, and abbreviation's background color.
 */
// The @Entity annotation generates a table in the database to store this information
@Entity(tableName = "contacts")
data class ContactDto(
    // The @PrimaryKey annotation identifies the primary key of the table
    // and enables it to be automatically generated
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val email: String,
    val phone: String,
    val abbreviation: String,
    val color: Int
)