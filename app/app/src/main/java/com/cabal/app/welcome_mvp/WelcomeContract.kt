package com.cabal.app.welcome_mvp


interface WelcomeContract {

    interface View {
        fun signIn()
        fun showLoginError()
        fun goToAfterRegister()
        fun showCoordinatesExplanation(show: Boolean)
    }

    interface Presenter {
        fun onGoogleButtonClicked()
        fun onLoginFinished(finished: Boolean)
        fun saveCoordinates(pair: Pair<Double, Double>)
    }

    interface Model {
        fun saveUser()
        fun saveCoordinates(pair: Pair<Double, Double>)
    }

}