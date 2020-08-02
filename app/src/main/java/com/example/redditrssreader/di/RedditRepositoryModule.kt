package com.example.redditrssreader.di

import com.example.redditrssreader.data.RedditRepository
import com.example.redditrssreader.room.RedditDatabase
import com.example.redditrssreader.service.RedditService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [RedditDatabaseModule::class, RedditServiceModule::class])
class RedditRepositoryModule {
    @Singleton
    @Provides
    fun redditRepository(database: RedditDatabase, redditService: RedditService) : RedditRepository {
        return RedditRepository(
            database,
            redditService
        )
    }
}