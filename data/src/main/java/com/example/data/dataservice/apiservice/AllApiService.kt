package com.example.data.dataservice.apiservice

import com.example.data.model.responce.*
import retrofit2.Response
import retrofit2.http.*

interface AllApiService {

    @GET("data/2.5/weather")
    suspend fun getData(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") app_id: String
    ): Response<WeatherData>
}
