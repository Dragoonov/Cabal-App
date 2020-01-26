package com.cabal.app.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
        @PrimaryKey val id: String,
        @ColumnInfo(name = "nick") val nick: String,
        @ColumnInfo(name = "email") val email: String,
        @ColumnInfo(name = "avatar") val avatar: String,
        @ColumnInfo(name = "radius") val radius: Int,
        @ColumnInfo(name = "coordinates") val coordinates: Pair<Double, Double>,
        @ColumnInfo(name = "hobbies") val hobbies: List<Hobby>
)