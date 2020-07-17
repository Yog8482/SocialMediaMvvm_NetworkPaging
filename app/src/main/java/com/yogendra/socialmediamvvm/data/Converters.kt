package com.yogendra.socialmediamvvm.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

open class Converters {
    @TypeConverter
    fun fromString(value: String): Any? {
        val type = object : TypeToken<Any>() {}.type
        return Gson().fromJson<Any>(value, type)
    }

    @TypeConverter
    fun fromObject(obj: Any?): String {
        val gson = Gson()
        return gson.toJson(obj)
    }
}