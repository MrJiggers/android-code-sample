package com.marmelade.android.spacex.data.entities.rocket

import androidx.room.Embedded
import com.squareup.moshi.Json


data class SecondStage(
	@Embedded(prefix = "thrust_") val thrust: Thrust?,
	@Embedded(prefix = "payloads_") val payloads: Payloads?,
	val reusable: Boolean?,
	val engines: Int?,
	@Json(name = "fuel_amount_tons") val fuelAmountTons: Double?,
	@Json(name = "burn_time_sec") val burnTimeSec: Double?
)