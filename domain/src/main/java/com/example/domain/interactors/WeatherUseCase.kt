package com.example.domain.interactors

import com.example.core.ActionResult
import com.example.domain.model.WeatherInfo
import kotlinx.coroutines.flow.*

interface WeatherUseCase {

    suspend operator fun invoke(lat: Double, lon: Double): Flow<ActionResult<WeatherInfo>>
}