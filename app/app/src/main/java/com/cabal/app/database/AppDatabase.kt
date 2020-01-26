package com.cabal.app.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cabal.app.database.daos.HobbyDao
import com.cabal.app.database.daos.UserDao
import com.cabal.app.database.entities.Hobby
import com.cabal.app.database.entities.User

@Database(entities = [User::class, Hobby::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun hobbyDao(): HobbyDao
}