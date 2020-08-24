package com.marmelade.android.spacex.data.entities.rocket

import androidx.room.Embedded
import com.squareup.moshi.Json


data class FirstStage(
	@Embedded(prefix = "thrust_seaLevel_") @Json(name = "thrust_seaLevel") val thrustSeaLevel: ThrustSeaLevel?,
	@Embedded(prefix = "thrust_vacuum_") @Json(name = "thrust_vacuum") val thrustVacuum: ThrustVacuum?,
	val reusable: Boolean?,
	val engines: Int?,
	@Json(name = "fuel_amount_tons") val fuelAmountTons: Double?,
	@Json(name = "burn_time_sec") val burnTimeSec: Double?
)