package com.cabal.app.Utils

import com.cabal.app.models.UserModel

object User {

    var loggedUser: UserModel? = null

    fun instantiate() {
        if( loggedUser == null) {
            loggedUser = UserModel()
        }
    }

}