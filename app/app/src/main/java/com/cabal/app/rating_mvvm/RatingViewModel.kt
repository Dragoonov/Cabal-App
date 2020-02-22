package com.cabal.app.rating_mvvm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.cabal.app.database.entities.User
import com.cabal.app.database.repository.Repository
import javax.inject.Inject

class RatingViewModel @Inject constructor(private val repository: Repository, private val app: Application) : AndroidViewModel(app) {

    lateinit var usersList: List<User>

    fun setLikeForUser(position: Int, number: Int) {
        usersList[position].likes = usersList[position].likes?.plus(number)
    }

    fun updateUsers() = repository.updateUsers(usersList)
}