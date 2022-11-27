package com.example.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import com.example.core.*
import com.example.data.dataservice.sqlservice.*
import com.example.data.datastore.*
import com.example.data.di.*
import com.example.data.repository.*
import com.example.domain.di.*
import com.example.domain.interactors.*
import com.example.domain.model.*
import com.example.domain.usecases.*
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.android.ext.koin.*
import org.koin.core.context.*
import org.koin.core.logger.*
import org.koin.dsl.*
import org.koin.test.*

class MyTestUseCase: KoinTest {

    val mainUseCase by inject<WeatherUseCase>()

    private val testCoroutineProvider = TestCoroutineDispatcherProvider()

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        androidContext(ApplicationProvider.getApplicationContext())
        modules(
            module {
               listOf(repositoryModule,
                   databaseModule,
                   apiModule,
                   interactorModule)
            })
    }

    /*
   * For make postValue and similar other-thread-posting-functions execute immediately
   */
    @get:Rule
    val instantTestExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        Dispatchers.setMain(testCoroutineProvider.getCoroutineDispatcher())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `unit test`() = runBlockingTest {
        val helloApp = mainUseCase(40.1758,44.5209).single()
        val mockItem = WeatherInfo(616052,"Yerevan",44.5209,40.1758,284.25,40)
        when(helloApp){
            is ActionResult.Success ->{
                assertEquals(mockItem, helloApp.data)
            }
        }
    }

    @Test
    fun `data correctly`() = runBlockingTest {
        Assert.assertEquals("pages", "pages")
    }
}