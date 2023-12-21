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

package upv.dadm.ex22_room.data.model

import upv.dadm.ex22_room.model.Contact

/**
 * Extension function to map a ContactDto instance to its domain counterpart.
 */
fun ContactDto.toDomain(): Contact =
    Contact(
        id = id,
        name = name,
        email = email,
        phone = phone,
        abbreviation = abbreviation,
        color = color
    )

/**
 * Extension function to map a Contact instance to its DTO counterpart.
 */
fun Contact.toDto(): ContactDto =
    ContactDto(
        id = id,
        name = name,
        email = email,
        phone = phone,
        abbreviation = abbreviation,
        color = color
    )
