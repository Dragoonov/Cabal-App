package com.cabal.app.di

import android.app.Application
import android.content.Context
import com.cabal.app.after_register_mvvm.AfterRegisterActivity
import com.cabal.app.welcome_mvvm.WelcomeActivity
import com.cabal.app.database.repository.AppRepository
import com.cabal.app.welcome_mvvm.WelcomeViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class, ViewModelModule::class])
@Singleton
interface AppComponent {

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance application: Application, @BindsInstance context: Context): AppComponent
    }


    fun inject(activity: WelcomeActivity)
    fun inject(activity: AfterRegisterActivity)
    fun welcomeModel(): AppRepository
    fun welcomeViewModel(): WelcomeViewModel
}