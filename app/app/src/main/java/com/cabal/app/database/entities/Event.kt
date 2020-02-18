package com.cabal.app.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Event(
        @PrimaryKey val id: Int,
        @ColumnInfo(name = "image") val image: String?,
        @ColumnInfo(name = "name") val name: String?,
        @ColumnInfo(name = "creator") val creator: User?,
        @ColumnInfo(name = "date") val date: Date?,
        @ColumnInfo(name = "members") val members: List<User>?,
        @ColumnInfo(name = "location") val location: String?,
        @ColumnInfo(name = "description") val description: String?,
        @ColumnInfo(name = "finished") val finished: Boolean?
)