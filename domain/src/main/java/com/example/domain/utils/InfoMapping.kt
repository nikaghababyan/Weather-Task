package com.example.domain.utils

import com.example.data.model.responce.*
import com.example.data.model.room.*
import com.example.domain.model.*

fun WeatherData.toDomain(): WeatherInfo =
	WeatherInfo(
		id?:0,
		name ?: "NULL_FIELD",
		coord?.lon ?: -1.0,
		coord?.lat ?: -1.0,
		main?.temp ?: -1.0,
		main?.humidity ?: -1
	)

fun WeatherData.toDomainEntity(): WeatherInfoEntity =
	WeatherInfoEntity(
		id?:0,
		name ?: "NULL_FIELD",
		coord?.lon ?: -1.0,
		coord?.lat ?: -1.0,
		main?.temp ?: -1.0,
		main?.humidity ?: -1
	)

fun WeatherInfoEntity.toDomain(): WeatherInfo =
	WeatherInfo(
		id,
		name,
		lon,
		lat,
		temp,
		humidity
	)