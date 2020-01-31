package com.cabal.app.database.repository

import com.cabal.app.database.entities.User

interface Repository {
    fun saveUser(user: User)

    fun createUser(user: User)
}