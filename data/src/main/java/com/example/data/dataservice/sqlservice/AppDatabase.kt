package com.example.data.dataservice.sqlservice

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.model.room.WeatherInfoEntity

@Database(
    entities = [WeatherInfoEntity::class],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun testDao(): WeatherDao
}