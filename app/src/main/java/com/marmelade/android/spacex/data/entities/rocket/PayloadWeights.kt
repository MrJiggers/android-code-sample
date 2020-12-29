package com.marmelade.android.spacex.data.entities.rocket


/**
 * @author Petr Tykal <tykal.pete@gmail.com>
 */
data class PayloadWeights(
        val id: String,
        val name: String?,
        val kg: Int?,
        val lb: Int?
)