package com.cabal.app.utils

import androidx.room.TypeConverter
import com.cabal.app.database.entities.Hobby
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object Converters {
    @TypeConverter
    @JvmStatic
    fun fromStringToDoubleArray(value: String): DoubleArray {
        val type = object : TypeToken<DoubleArray>() {}.type
        return Gson().fromJson<DoubleArray>(value, type)
    }

    @TypeConverter
    @JvmStatic
    fun fromDoubleArray(array: DoubleArray) = Gson().toJson(array)

    @TypeConverter
    @JvmStatic
    fun fromStringToHobby(value: String): List<Hobby> {
        val type = object : TypeToken<List<Hobby>>() {}.type
        return Gson().fromJson<List<Hobby>>(value, type)
    }

    @TypeConverter
    @JvmStatic
    fun fromHobbyList(list: List<Hobby>) = Gson().toJson(list)
}