package com.cabal.app.welcome_mvvm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.cabal.app.database.entities.User
import com.cabal.app.database.repository.AppRepository
import javax.inject.Inject

class WelcomeViewModel @Inject constructor(private val model: AppRepository, private val app: Application): AndroidViewModel(app) {

    fun onLoginFinished() {
      // model.saveUser()
    }

    fun checkIfUserLoggedIn(id: String) = model.checkIfUserLoggedIn(id)

    fun getUserById(id: String) = model.getUserById(id)

    fun createUser(user: User) = model.createUser(user)

    fun saveCoordinates(pair: Pair<Double, Double>) {
        // CHECKING LOGIC???
        model.saveCoordinates(pair)
    }

}