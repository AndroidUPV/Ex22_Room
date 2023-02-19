/*
 * Copyright (c) 2022
 * David de Andrés and Juan Carlos Ruiz
 * Development of apps for mobile devices
 * Universitat Politècnica de València
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
