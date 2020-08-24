package com.marmelade.android.spacex.data.entities.rocket

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.marmelade.android.spacex.data.database.DatabaseTypeConverters
import com.squareup.moshi.Json


@Entity
data class Rocket(
        @PrimaryKey val id: String,
        @Embedded(prefix = "height_") val height: Height?,
        @Embedded(prefix = "diameter_") val diameter: Diameter?,
        @Embedded(prefix = "mass_") val mass: Mass?,
        @Embedded(prefix = "first_stage_") @Json(name = "first_stage") val firstStage: FirstStage?,
        @Embedded(prefix = "second_stage_") @Json(name = "second_stage") val secondStage: SecondStage?,
        @Embedded(prefix = "engines_") val engines: Engines?,
        @Embedded(prefix = "landing_legs_") @Json(name = "landing_legs") val landingLegs: LandingLegs?,
        @TypeConverters(DatabaseTypeConverters::class) @Json(name = "payload_weights") val payloadWeights: List<PayloadWeights>?,
        @TypeConverters(DatabaseTypeConverters::class) @Json(name = "flickr_images") val flickrImages: List<String>?,
        val name: String?,
        val type: String?,
        val active: Boolean?,
        val stages: Int?,
        val boosters: Int?,
        @Json(name = "cost_per_launch") val costPerLaunch: Int?,
        @Json(name = "success_rate_pct") val successRatePct: Int?,
        @Json(name = "first_flight") val firstFlight: String?,
        val country: String?,
        val company: String?,
        val wikipedia: String?,
        val description: String?
)