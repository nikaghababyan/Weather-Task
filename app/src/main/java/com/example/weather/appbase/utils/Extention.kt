package com.example.weather.appbase.utils

import androidx.lifecycle.LifecycleOwner
import com.example.weather.appbase.viewmodel.FlowObserver
import kotlinx.coroutines.flow.Flow

inline fun <reified T> Flow<T>.observeInLifecycle(
    lifecycleOwner: LifecycleOwner
) = FlowObserver(lifecycleOwner, this)