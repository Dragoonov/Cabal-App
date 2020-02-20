package com.cabal.app.database.repository

import android.content.Context
import androidx.room.Room
import com.cabal.app.database.AppDatabase
import com.cabal.app.database.daos.EventDao
import com.cabal.app.database.daos.HobbyDao
import com.cabal.app.database.daos.UserDao
import com.cabal.app.database.entities.Event
import com.cabal.app.database.entities.User
import dagger.Provides
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalRepository @Inject constructor(context: Context): Repository {

    private val userDao: UserDao
    private val hobbyDao: HobbyDao
    private val eventDao: EventDao

    init {
        val db = AppDatabase.getInstance(context)
        userDao = db.userDao()
        hobbyDao = db.hobbyDao()
        eventDao = db.eventDao()
    }

    override fun updateUser(user: User): Completable {
        return userDao.updateUser(user)
    }

    override fun createUser(user: User): Completable = userDao.insertUser(user)

    override fun getUserById(id: String) = userDao.getUserById(id)

    override fun checkIfUserLoggedIn(id: String) = userDao.checkIfUserLoggedIn(id)

    override fun insertEvents(events: List<Event>): Completable = eventDao.insertEvents(events)

    override fun getEventsByAccepted(accepted: Boolean): Single<List<Event>> = eventDao.getEventsByAccepted(accepted)
}