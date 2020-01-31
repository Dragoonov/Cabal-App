package com.cabal.app.welcome_mvvm

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import com.cabal.app.database.entities.User

class WelcomeViewModel(app: Application): ViewModel() {

    private val model = WelcomeModel(app)

    fun onLoginFinished() {
      // model.saveUser()
    }

    fun checkIfUserLoggedIn(app: Application, id: String) = model.checkIfUserLoggedIn(app, id)

    fun getUserById(context: Context, id: String): User = model.getUserById(context, id)

    fun createUser(user: User) = model.createUser(user)

    fun saveCoordinates(pair: Pair<Double, Double>) {
        // CHECKING LOGIC???
        model.saveCoordinates(pair)
    }

}