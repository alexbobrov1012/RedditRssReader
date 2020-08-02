package com.example.redditrssreader.di

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: Application) {
    @Singleton
    @Provides
    fun application(): Application {
        return application
    }
}