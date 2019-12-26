package com.cabal.app.welcome_mvp

class WelcomePresenter(view: WelcomeContract.View) : WelcomeContract.Presenter {

    val view: WelcomeContract.View? = view
    val model: WelcomeContract.Model? = WelcomeModel()

    override fun onGoogleButtonClicked() {
        view?.signIn()
    }

    override fun onLoginFinished(finished: Boolean) {
        if (finished) {
            model?.saveUser()
            view?.goToAfterRegister()
        }
        else view?.showLoginError()
    }

    override fun saveCoordinates(pair: Pair<Double, Double>) {
        // CHECKING LOGIC???
        model?.saveCoordinates(pair)
    }

}