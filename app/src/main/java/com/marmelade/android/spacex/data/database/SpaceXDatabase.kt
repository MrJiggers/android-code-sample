package com.marmelade.android.spacex.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.marmelade.android.spacex.data.entities.company.Company
import com.marmelade.android.spacex.data.entities.rocket.Rocket


/**
 * @author Petr Tykal <tykal.pete@gmail.com>
 */
@Database(
        entities = [
            Rocket::class,
            Company::class
        ],
        version = 1,
        exportSchema = true
)
@TypeConverters(DatabaseTypeConverters::class)
abstract class SpaceXDatabase : RoomDatabase() {

    abstract fun rocketsDao(): RocketsDao
    abstract fun companyDao(): CompanyDao
}