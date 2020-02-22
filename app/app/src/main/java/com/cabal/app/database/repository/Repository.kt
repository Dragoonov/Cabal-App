package com.cabal.app.database.repository

import com.cabal.app.database.entities.Event
import com.cabal.app.database.entities.User
import io.reactivex.Completable
import io.reactivex.Single

interface Repository {
    fun updateUser(user: User): Completable

    fun createUser(user: User): Completable

    fun getUserById(id: String): Single<User>

    fun checkIfUserLoggedIn(id: String): Single<Boolean>

    fun insertEvents(events: List<Event>): Completable

    fun getEventsByAccepted(accepted: Boolean): Single<List<Event>>

    fun getEvents(): Single<List<Event>>

    fun updateUsers(users: List<User>): Completable
}