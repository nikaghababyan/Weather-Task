package com.example.data.datastore

import com.example.core.ActionResult
import com.example.data.model.responce.*

interface WeatherRepository {

    suspend fun getWeatherData(lat: Double, lon: Double): ActionResult<WeatherData>
}