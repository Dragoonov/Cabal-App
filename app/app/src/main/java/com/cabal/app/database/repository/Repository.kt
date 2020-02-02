package com.cabal.app.database.repository

import com.cabal.app.database.entities.User
import io.reactivex.Completable

interface Repository {
    fun saveUser(user: User): Completable

    fun createUser(user: User): Completable
}