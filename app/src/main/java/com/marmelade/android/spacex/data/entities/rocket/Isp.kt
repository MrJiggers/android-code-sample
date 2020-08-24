package com.marmelade.android.spacex.data.entities.rocket

import com.squareup.moshi.Json


data class Isp(
		@Json(name = "sea_level") val seaLevel: Int?,
		val vacuum: Int?
)