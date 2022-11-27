package com.example.weather.util

import androidx.annotation.*

class MapCompat {

	fun <K, V> getOrDefault(@NonNull map: Map<K, V>, key: K, defaultValue: V): V {
		var v: V
		return if (map[key].also { v = it!! } != null || map.containsKey(key)) v else defaultValue
	}
}