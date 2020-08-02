package com.example.redditrssreader.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.redditrssreader.model.RedditEntity

@Database(entities = [RedditEntity::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class RedditDatabase : RoomDatabase() {
    abstract fun redditDao(): RedditDao
}