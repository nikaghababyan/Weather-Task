package com.example.data.repository

import com.example.data.dataservice.apiservice.AllApiService
import com.example.data.datastore.WeatherRepository
import com.example.data.util.analyzeResponse
import com.example.data.util.makeApiCall
import com.example.core.ActionResult
import com.example.data.model.responce.*
import com.example.data.util.Constants.Companion.API_KEY

class WeatherRepositoryImpl(private val allApiService: AllApiService) : WeatherRepository {

    override suspend fun getWeatherData(lat: Double, lon: Double): ActionResult<WeatherData> =
        makeApiCall({
            analyzeResponse(allApiService.getData(lat,lon, API_KEY))
        })

}