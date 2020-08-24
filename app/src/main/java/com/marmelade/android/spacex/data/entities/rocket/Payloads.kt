package com.marmelade.android.spacex.data.entities.rocket

import androidx.room.Embedded
import com.squareup.moshi.Json


data class Payloads(
	@Embedded(prefix = "compositeFairing_") @Json(name = "composite_fairing") val compositeFairing: CompositeFairing?,
	@Json(name = "option_1") val option1: String?
)