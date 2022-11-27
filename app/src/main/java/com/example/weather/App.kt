package com.example.weather

import android.app.Application
import com.example.data.di.apiModule
import com.example.data.di.databaseModule
import com.example.data.di.repositoryModule
import com.example.domain.di.interactorModule
import com.example.weather.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.*

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            modules(modules)
        }
    }

    private val modules = listOf(
        apiModule,
        databaseModule,
        repositoryModule,
        interactorModule,
        viewModelModule,
        supportClasses
    )
}