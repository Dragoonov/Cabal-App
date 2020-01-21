package com.cabal.app.welcome_mvp

import com.cabal.app.Utils.User

class WelcomeModel : WelcomeContract.Model {

    init {
        User.instantiate()
    }

    override fun saveUser() {

    }

    override fun saveCoordinates(pair: Pair<Double, Double>) {
        User.loggedUser?.coordinates = doubleArrayOf(pair.first, pair.second)
    }
}