package com.cabal.app.welcome_mvvm

import com.cabal.app.Utils.User

class WelcomeModel {

    init {
        User.instantiate()
    }

    fun saveUser() {

    }

    fun saveCoordinates(pair: Pair<Double, Double>) {
        User.loggedUser?.coordinates = doubleArrayOf(pair.first, pair.second)
    }
}