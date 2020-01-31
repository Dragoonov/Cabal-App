package com.cabal.app.database.repository

import android.content.Context
import androidx.room.Room
import com.cabal.app.database.AppDatabase
import com.cabal.app.database.daos.HobbyDao
import com.cabal.app.database.daos.UserDao
import com.cabal.app.database.entities.User

class LocalRepository(context: Context): Repository {

    private val userDao: UserDao
    private val hobbyDao: HobbyDao

    init {
        val db = AppDatabase.getInstance(context)
        userDao = db.userDao()
        hobbyDao = db.hobbyDao()
    }

    override fun saveUser(user: User) {
        //userDao.insertUser(user)
    }

    override fun createUser(user: User) {
        userDao.insertUser(user)
    }

    fun getUserById(id: String) = userDao.getUserById(id)

    fun checkIfUserLoggedIn(id: String) = userDao.checkIfUserLoggedIn(id)
}