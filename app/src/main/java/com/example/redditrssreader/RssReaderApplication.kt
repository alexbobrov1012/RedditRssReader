package com.example.redditrssreader

import android.app.Application
import com.example.redditrssreader.di.ApplicationModule
import com.example.redditrssreader.di.DaggerRedditPresenterComponent
import com.example.redditrssreader.di.RedditPresenterComponent

class RssReaderApplication : Application()  {
    lateinit var presenterComponent: RedditPresenterComponent
    override fun onCreate() {
        super.onCreate()
        presenterComponent = DaggerRedditPresenterComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }
}