package com.marmelade.android.spacex.data.entities.rocket

import androidx.room.Embedded
import com.squareup.moshi.Json


/**
 * @author Petr Tykal <tykal.pete@gmail.com>
 */
data class Engines(
		@Embedded(prefix = "isp_") val isp: Isp,
		@Embedded(prefix = "thrust_seaLevel_") @Json(name = "thrust_seaLevel") val thrustSeaLevel: ThrustSeaLevel?,
		@Embedded(prefix = "thrust_vacuum_") @Json(name = "thrust_vacuum") val thrustVacuum: ThrustVacuum?,
		val number: Int?,
		val type: String?,
		val version: String?,
		val layout: String?,
		@Json(name = "engine_loss_max") val engineLossMax: Int?,
		@Json(name = "propellant_1") val propellant1: String?,
		@Json(name = "propellant_2") val propellant2: String?,
		@Json(name = "thrust_to_weight") val thrustToWeight: Double?
)