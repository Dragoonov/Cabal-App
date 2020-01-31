package com.cabal.app.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Hobby(
        @PrimaryKey val name: String,
        @ColumnInfo(name = "description") val description: String?
)