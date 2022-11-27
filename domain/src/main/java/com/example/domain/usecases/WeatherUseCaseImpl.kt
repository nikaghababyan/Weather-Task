package com.example.domain.usecases

import com.example.core.*
import com.example.core.dispatcher.*
import com.example.data.dataservice.sqlservice.*
import com.example.data.datastore.*
import com.example.data.util.Constants.Companion.ERROR_DATA
import com.example.domain.interactors.*
import com.example.domain.model.*
import com.example.domain.utils.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class WeatherUseCaseImpl(
	private val weatherRepository: WeatherRepository,
	private val weatherDao: WeatherDao,
	private val dispatcher: CoroutineDispatcherProvider
) : WeatherUseCase {

	override suspend fun invoke(lat: Double, lon: Double) = channelFlow<ActionResult<WeatherInfo>> {
		withContext(dispatcher.io) {
			val weatherInfo = weatherDao.getWeather()
			if(weatherInfo!=null){
				send(ActionResult.Success(weatherInfo.toDomain()))
			}

			when (val apiData = weatherRepository.getWeatherData(lat, lon)) {
				is ActionResult.Success -> {
					val dto = apiData.data.toDomainEntity()
					weatherDao.insertWeather(dto)
					send(ActionResult.Success(apiData.data.toDomain()))
				}
				is ActionResult.Error -> {
					send(
						ActionResult.Error(
							CallException(
								ERROR_DATA,
								apiData.errors.errorMessage
							)
						)
					)
				}
			}
		}
	}
}