package com.example.data.dataservice.appservice

import android.content.Context
import android.content.SharedPreferences
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi

class PreferenceServiceImpl(private val context: Context) : PreferenceService {

    private val authenticationKey = "authenticationKey"
    val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("userData", Context.MODE_PRIVATE)

//    override fun getUserDataPref(): OwnerUserData {
//        val jsonAdapter: JsonAdapter<OwnerUserData> = Moshi.Builder().build().adapter(OwnerUserData::class.java)
//        val json = context.getSharedPreferences("userData", Context.MODE_PRIVATE).getString(authenticationKey, "")
//        return jsonAdapter.fromJson(json!!)!!
//    }
//
//    override fun setUserDataPref(userData: OwnerUserBalance) {
//        val jsonAdapter: JsonAdapter<OwnerUserData> = Moshi.Builder().build().adapter(OwnerUserData::class.java)
//        context.getSharedPreferences("userData", Context.MODE_PRIVATE).edit()
//                .putString(authenticationKey, jsonAdapter.toJson(userData)).apply()
//    }

    inline fun <reified T> get(key: String): T? {
        val value = sharedPreferences.getString(key, null)
        return value?.let {
            val jsonAdapter: JsonAdapter<T> =
                Moshi.Builder().build().adapter(T::class.java)
            jsonAdapter.fromJson(it)
        }
    }

    inline fun <reified T> put(data: T, key: String) {
        val jsonAdapter: JsonAdapter<T> =
            Moshi.Builder().build().adapter(T::class.java)
        val jsonString = jsonAdapter.toJson(data)
        return sharedPreferences.set(key, jsonString)
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = this.edit()
        operation(editor)
        editor.apply()
    }

    operator fun SharedPreferences.set(key: String, value: Any?) {
        when (value) {
            is Int -> edit { it.putInt(key, value) }
            is Long -> edit { it.putLong(key, value) }
            is Float -> edit { it.putFloat(key, value) }
            is String -> edit { it.putString(key, value) }
            is Boolean -> edit { it.putBoolean(key, value) }
            else -> throw UnsupportedOperationException("Not implemented type")
        }
    }

    inline operator fun <reified T : Any> SharedPreferences.get(key: String, defaultValue: T): T {
        return when (T::class) {
            Int::class -> getInt(key, defaultValue as Int) as T
            Long::class -> getLong(key, defaultValue as Long) as T
            Float::class -> getFloat(key, defaultValue as Float) as T
            String::class -> getString(key, defaultValue as String) as T
            Boolean::class -> getBoolean(key, defaultValue as Boolean) as T
            else -> throw UnsupportedOperationException("Not implemented type")
        }
    }

}