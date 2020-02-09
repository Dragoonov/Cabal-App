package com.cabal.app.di

import com.cabal.app.database.repository.AppRepository
import com.cabal.app.database.repository.Repository
import dagger.Binds
import dagger.Module

@Module
abstract class AppModule {

    @Binds
    abstract fun provideRepository(appRepository: AppRepository): Repository
}