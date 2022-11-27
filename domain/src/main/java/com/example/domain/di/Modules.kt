package com.example.domain.di


import com.example.core.dispatcher.BaseCoroutineDispatcherProvider
import com.example.core.dispatcher.CoroutineDispatcherProvider
import com.example.domain.interactors.*
import com.example.domain.usecases.*
import org.koin.dsl.module

val interactorModule = module {
    single<CoroutineDispatcherProvider> { BaseCoroutineDispatcherProvider() }
    factory<WeatherUseCase> { WeatherUseCaseImpl(get(),get(), get()) }
}
