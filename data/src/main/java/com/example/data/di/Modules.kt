package com.example.data.di

import android.app.*
import androidx.room.*
import com.example.data.dataservice.apiservice.AllApiService
import com.example.data.dataservice.sqlservice.*
import com.example.data.datastore.WeatherRepository
import com.example.data.repository.WeatherRepositoryImpl
import com.example.data.util.Constants.Companion.BASE_URL
import com.example.data.util.HeaderInterceptor
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val apiModule = module {

    single { Moshi.Builder().build() }
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .apply {
                client(
                    OkHttpClient.Builder()
                        .addInterceptor(HeaderInterceptor())
                        .addInterceptor(HttpLoggingInterceptor().apply {
                            level = HttpLoggingInterceptor.Level.BODY
                        })
                        .build()
                )
            }
            .build()
    }

    single<AllApiService> { get<Retrofit>().create(AllApiService::class.java) }

}

val databaseModule = module {

    fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "WeatherDB")
            // .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }
    single { provideDatabase(androidApplication()) }
    single { get<AppDatabase>().testDao() }

}

val repositoryModule = module {
    single<WeatherRepository> { WeatherRepositoryImpl(get()) }
}


