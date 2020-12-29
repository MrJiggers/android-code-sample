package com.marmelade.android.spacex.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.marmelade.android.spacex.data.entities.rocket.Rocket


/**
 * @author Petr Tykal <tykal.pete@gmail.com>
 */
@Dao
interface RocketsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items: Rocket)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(items: List<Rocket>)

    @Query("SELECT * FROM Rocket WHERE :rocketId = id")
    fun getRocketById(rocketId: String): LiveData<List<Rocket>>

    @Query("SELECT * FROM Rocket ORDER BY firstFlight DESC")
    fun getAllRockets(): LiveData<List<Rocket>>

    @Query("SELECT * FROM Rocket WHERE (:active = active or (:active is null)) AND (successRatePct >= :successRatio or (:successRatio is null)) ORDER BY firstFlight DESC")
    fun getAllRocketsFiltered(active: Boolean?, successRatio: Int?): LiveData<List<Rocket>>
}