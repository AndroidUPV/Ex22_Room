/*
 * Copyright (c) 2022-2026 Universitat Politècnica de València
 * Authors: David de Andrés and Juan Carlos Ruiz
 *          Fault-Tolerant Systems
 *          Instituto ITACA
 *          Universitat Politècnica de València
 *
 * Distributed under MIT license
 * (See accompanying file LICENSE.txt)
 */

package upv.dadm.room

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Custom Application required for Hilt to inject dependencies.
 * The Manifest must declare it: <application android:name = ".AccessRestfulWebServiceApplication"/>
 */
// The Hilt annotation @HiltAndroidApp identifies this class as the dependency container
@HiltAndroidApp
class RoomApplication : Application()