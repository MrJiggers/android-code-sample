package com.marmelade.android.spacex.data.entities.company

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json


/**
 * @author Petr Tykal <tykal.pete@gmail.com>
 */
@Entity
data class Company(
	@PrimaryKey val id: String,
	@Embedded(prefix = "headquarters_") val headquarters: Headquarters?,
	@Embedded(prefix = "links_") val links: Links?,
	val name: String?,
	val founder: String?,
	val founded: Int?,
	val employees: Int?,
	val vehicles: Int?,
	@Json(name = "launch_sites") val launchSites: Int?,
	@Json(name = "test_sites") val testSites: Int?,
	val ceo: String?,
	val cto: String?,
	val coo: String?,
	@Json(name = "cto_propulsion") val ctoPropulsion: String?,
	val valuation: Long?,
	val summary: String?
)