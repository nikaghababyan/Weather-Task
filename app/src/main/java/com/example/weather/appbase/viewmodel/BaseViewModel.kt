package com.example.weather.appbase.viewmodel

import androidx.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

abstract class BaseViewModel : ViewModel() {

	private val _errorNullData by lazy { MutableSharedFlow<String>() }
	val errorNullData get() = _errorNullData.asSharedFlow()

	fun onError(errorMessage:String){
		viewModelScope.launch {
			_errorNullData.emit(errorMessage)
		}
	}


}