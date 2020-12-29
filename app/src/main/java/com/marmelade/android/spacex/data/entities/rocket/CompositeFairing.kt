package com.marmelade.android.spacex.data.entities.rocket

import androidx.room.Embedded


/**
 * @author Petr Tykal <tykal.pete@gmail.com>
 */
data class CompositeFairing(
        @Embedded(prefix = "height_") val height: Height?,
        @Embedded(prefix = "diameter_") val diameter: Diameter?
)