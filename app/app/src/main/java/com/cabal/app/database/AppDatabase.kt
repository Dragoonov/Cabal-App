package com.cabal.app.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.cabal.app.utils.Converters
import com.cabal.app.database.daos.HobbyDao
import com.cabal.app.database.daos.UserDao
import com.cabal.app.database.entities.Hobby
import com.cabal.app.database.entities.User

@Database(entities = [User::class, Hobby::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun hobbyDao(): HobbyDao

    companion object {

        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
                }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                        AppDatabase::class.java, "Sample.db")
                        .build()
    }
}