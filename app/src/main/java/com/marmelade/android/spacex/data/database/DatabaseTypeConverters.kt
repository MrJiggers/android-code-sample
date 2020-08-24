package com.marmelade.android.spacex.data.database

import androidx.room.TypeConverter
import com.marmelade.android.spacex.data.entities.rocket.PayloadWeights
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types


class DatabaseTypeConverters {
    val moshi: Moshi = Moshi.Builder().build()

    @TypeConverter
    fun listPayloadWeightsFromString(data: String): List<PayloadWeights>? {
        val type = Types.newParameterizedType(List::class.java, PayloadWeights::class.java)
        val adapter = moshi.adapter<List<PayloadWeights>>(type)
        return adapter.fromJson(data)
    }

    @TypeConverter
    fun listPayloadWeightsToString(data: List<PayloadWeights>?): String {
        val type = Types.newParameterizedType(List::class.java, PayloadWeights::class.java)
        val adapter = moshi.adapter<List<PayloadWeights>>(type)
        return adapter.toJson(data)
    }

    @TypeConverter
    fun listStringFromString(data: String): List<String>? {
        val type = Types.newParameterizedType(List::class.java, String::class.java)
        val adapter = moshi.adapter<List<String>>(type)
        return adapter.fromJson(data)
    }

    @TypeConverter
    fun listStringToString(data: List<String>?): String {
        val type = Types.newParameterizedType(List::class.java, String::class.java)
        val adapter = moshi.adapter<List<String>>(type)
        return adapter.toJson(data)
    }
}