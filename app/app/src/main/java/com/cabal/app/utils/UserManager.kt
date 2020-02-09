package com.cabal.app.utils

import com.cabal.app.database.entities.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserManager @Inject constructor() {

    var loggedUser: User? = null

    fun login(user: User) = run { loggedUser = user }
    fun logout() = run { loggedUser = null }

}