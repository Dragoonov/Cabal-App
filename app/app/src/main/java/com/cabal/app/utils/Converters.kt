package com.cabal.app.utils

import androidx.room.TypeConverter
import com.cabal.app.database.entities.Hobby
import com.cabal.app.database.entities.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

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

    @TypeConverter
    @JvmStatic
    fun fromUserList(list: List<User>) = Gson().toJson(list)

    @TypeConverter
    @JvmStatic
    fun fromStrintToUserList(value: String): List<User> {
        val type = object : TypeToken<List<User>>() {}.type
        return Gson().fromJson<List<User>>(value, type)
    }

    @TypeConverter
    @JvmStatic
    fun fromDate(date: Date) = Gson().toJson(date)

    @TypeConverter
    @JvmStatic
    fun fromStringToDate(value: String): Date {
        val type = object : TypeToken<Date>() {}.type
        return Gson().fromJson<Date>(value, type)
    }
}