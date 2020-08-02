package com.example.redditrssreader.di

import android.app.Application
import androidx.room.Room
import com.example.redditrssreader.room.RedditDao
import com.example.redditrssreader.room.RedditDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ApplicationModule::class])
class RedditDatabaseModule {
    @Singleton
    @Provides
    fun newsDatabase(application: Application): RedditDatabase {
        return Room
            .databaseBuilder(application, RedditDatabase::class.java, "news_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun redditDao(database: RedditDatabase): RedditDao {
        return database.redditDao()
    }
}