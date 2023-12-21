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

import upv.dadm.ex22_room.model.ContactBrief

/**
 * Extension function to map a ContactBriefDto instance to its domain counterpart.
 */
fun ContactBriefDto.toDomain(): ContactBrief =
    ContactBrief(
        id = id,
        name = name,
        abbreviation = abbreviation,
        color = color
    )
