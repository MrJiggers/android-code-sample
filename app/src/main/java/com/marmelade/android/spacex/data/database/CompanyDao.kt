package com.marmelade.android.spacex.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.marmelade.android.spacex.data.entities.company.Company


/**
 * @author Petr Tykal <tykal.pete@gmail.com>
 */
@Dao
interface CompanyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items: Company)

    @Query("SELECT * FROM Company LIMIT 1")
    fun getCompany(): LiveData<List<Company>>
}