package com.yogendra.socialmediamvvm.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

open class Converters {
//    @TypeConverter
//    fun fromString(value: String?): List<Any?>?> {
//        val type = object : TypeToken<Any>() {}.type
//        return Gson().fromJson<Any>(value, type)
//    }
//
//    @TypeConverter
//    fun fromObject(obj: Any?): String {
//        val gson = Gson()
//        return gson.toJson(obj)
//    }


    @TypeConverter
     fun fromStringArticles(value: String?): List<ArticleMedia?> {
        val listType = object : TypeToken<List<ArticleMedia?>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
     fun fromListArticles(list: List<ArticleMedia?>): String? {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
     fun fromStringUsers(value: String?): List<Users?> {
        val listType = object : TypeToken<List<Users?>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
     fun fromListUsers(list: List<Users?>): String? {
        val gson = Gson()
        return gson.toJson(list)
    }
}