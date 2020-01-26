package com.cabal.app.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class Hobby(
        @ColumnInfo(name = "name") val name: String?,
        @ColumnInfo(name = "description") val description: String?
)