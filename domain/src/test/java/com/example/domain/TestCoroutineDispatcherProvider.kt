package com.example.domain

import com.example.core.dispatcher.CoroutineDispatcherProvider
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlin.coroutines.CoroutineContext

class TestCoroutineDispatcherProvider : CoroutineDispatcherProvider {

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    override val main: CoroutineContext = testCoroutineDispatcher

    override val io: CoroutineContext = testCoroutineDispatcher

    fun getCoroutineDispatcher() = testCoroutineDispatcher
}