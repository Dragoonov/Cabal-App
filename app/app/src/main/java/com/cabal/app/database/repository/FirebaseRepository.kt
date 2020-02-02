package com.cabal.app.database.repository

import com.cabal.app.database.entities.User
import io.reactivex.Completable

class FirebaseRepository: Repository {
    override fun saveUser(user: User): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createUser(user: User): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}