package com.cabal.app.database.repository

import android.content.Context
import androidx.room.Room
import com.cabal.app.database.AppDatabase
import com.cabal.app.database.daos.HobbyDao
import com.cabal.app.database.daos.UserDao
import com.cabal.app.database.entities.User
import dagger.Provides
import io.reactivex.Completable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalRepository @Inject constructor(context: Context): Repository {

    private val userDao: UserDao
    private val hobbyDao: HobbyDao

    init {
        val db = AppDatabase.getInstance(context)
        userDao = db.userDao()
        hobbyDao = db.hobbyDao()
    }

    override fun updateUser(user: User): Completable {
        return userDao.updateUser(user)
    }

    override fun createUser(user: User): Completable = userDao.insertUser(user)

    override fun getUserById(id: String) = userDao.getUserById(id)

    override fun checkIfUserLoggedIn(id: String) = userDao.checkIfUserLoggedIn(id)
}