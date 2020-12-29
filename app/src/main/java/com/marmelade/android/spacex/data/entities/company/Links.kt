package com.marmelade.android.spacex.data.entities.company

import com.squareup.moshi.Json


/**
 * @author Petr Tykal <tykal.pete@gmail.com>
 */
data class Links(
        val website: String?,
        val flickr: String?,
        val twitter: String?,
        @Json(name = "elonTwitter") val elonTwitter: String?
)