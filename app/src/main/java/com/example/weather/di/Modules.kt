package com.example.weather.di

import com.example.weather.manager.LocationUpdateManager
import com.example.weather.ui.fragment.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get(), get()) }
}

val supportClasses = module {
    single { LocationUpdateManager(get()) }
}
