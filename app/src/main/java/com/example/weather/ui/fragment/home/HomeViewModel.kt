package com.example.weather.ui.fragment.home

import android.*
import android.util.*
import androidx.activity.result.contract.*
import androidx.lifecycle.viewModelScope
import com.example.weather.appbase.viewmodel.BaseViewModel
import com.example.core.ActionResult
import com.example.domain.interactors.WeatherUseCase
import com.example.domain.model.WeatherInfo
import com.example.weather.manager.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(private val locationManager: LocationUpdateManager, private val weatherUseCase: WeatherUseCase) : BaseViewModel() {

    private val _weatherInfo: MutableStateFlow<WeatherInfo?> by lazy { MutableStateFlow(null) }
    val weatherInfo= _weatherInfo.asStateFlow()

    private val _weatherTemp: MutableStateFlow<Double?> by lazy { MutableStateFlow(null) }
    val weatherTemp= _weatherTemp.asStateFlow()

    private val _progress: MutableSharedFlow<Boolean> by lazy { MutableSharedFlow(replay = 1) }
    val progress = _progress.asSharedFlow()


    fun getWeatherInfo() {



        viewModelScope.launch {
            _progress.emit(true)
            locationManager.checkLocation()
            locationManager.coordinatesData = { lat: Double, lon: Double ->
                viewModelScope.launch {
                    weatherUseCase(lat, lon).collect {
                        when (it) {
                            is ActionResult.Success -> {
                                _weatherTemp.emit(it.data.temp)
                                _weatherInfo.emit(it.data)
                            }
                            is ActionResult.Error -> {
                                onError(it.errors.errorMessage!!)
                            }
                        }
                        _progress.emit(false)
                    }
                }
            }
        }
    }

    fun onWeatherTemp(celsius: Double) {
        viewModelScope.launch {
            _weatherTemp.emit(celsius)
        }
    }

}