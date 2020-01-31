package com.cabal.app.welcome_mvvm

import android.content.Context
import com.cabal.app.database.entities.User
import com.cabal.app.database.repository.LocalRepository
import com.cabal.app.database.repository.Repository

class WelcomeModel(context: Context) {

    private var repository: Repository = LocalRepository(context)

    fun checkIfUserLoggedIn(context: Context, id: String): Boolean {
        val repo = LocalRepository(context)
        return repo.checkIfUserLoggedIn(id)
    }

    fun getUserById(context: Context, id: String): User {
        val repo = LocalRepository(context)
        return repo.getUserById(id)
    }

    fun createUser(user: User) = repository.createUser(user)

    fun saveCoordinates(pair: Pair<Double, Double>) {
       // User.loggedUser?.coordinates = doubleArrayOf(pair.first, pair.second)
    }
}