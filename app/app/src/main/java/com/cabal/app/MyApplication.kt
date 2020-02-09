package com.cabal.app

import android.app.Application
import com.cabal.app.di.AppComponent
import com.cabal.app.di.DaggerAppComponent

open class MyApplication: Application() {

    val appComponent: AppComponent by lazy {
        // Creates an instance of AppComponent using its Factory constructor
        // We pass the applicationContext that will be used as Context in the graph
        DaggerAppComponent.factory().create(this,applicationContext)
    }
}