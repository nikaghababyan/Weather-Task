package com.example.data.dataservice.sqlservice

import androidx.room.*
import com.example.data.model.room.*

@Dao
interface WeatherDao {

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insertWeather(item: WeatherInfoEntity)

	@Query("SELECT * FROM weatherInfoTB")
	fun getWeather(): WeatherInfoEntity?

}