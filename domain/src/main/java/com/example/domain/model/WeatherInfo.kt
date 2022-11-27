package com.example.domain.model

data class WeatherInfo(
    val id: Int,
    val name: String,
    val lon: Double,
    val lat: Double,
    val temp: Double,
    val humidity: Int
)