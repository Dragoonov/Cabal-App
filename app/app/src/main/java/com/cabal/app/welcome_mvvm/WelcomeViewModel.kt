package com.cabal.app.welcome_mvvm

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class WelcomeViewModel(app: Application): AndroidViewModel(app) {

    private val model = WelcomeModel()

    fun onLoginFinished() {
        model.saveUser()
    }

    fun saveCoordinates(pair: Pair<Double, Double>) {
        // CHECKING LOGIC???
        model.saveCoordinates(pair)
    }

}