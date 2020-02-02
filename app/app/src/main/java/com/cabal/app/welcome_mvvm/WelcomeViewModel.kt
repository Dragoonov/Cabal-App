package com.cabal.app.welcome_mvvm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.cabal.app.database.entities.User

class WelcomeViewModel(app: Application): AndroidViewModel(app) {

    private val model = WelcomeModel(app)

    fun onLoginFinished() {
      // model.saveUser()
    }

    fun checkIfUserLoggedIn(id: String) = model.checkIfUserLoggedIn(getApplication(), id)

    fun getUserById(id: String) = model.getUserById(getApplication(), id)

    fun createUser(user: User) = model.createUser(user)

    fun saveCoordinates(pair: Pair<Double, Double>) {
        // CHECKING LOGIC???
        model.saveCoordinates(pair)
    }

}