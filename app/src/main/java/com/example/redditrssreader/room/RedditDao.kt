package com.example.redditrssreader.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.redditrssreader.model.RedditEntity

@Dao
interface RedditDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(newsList: List<RedditEntity>)

    @Query("DELETE FROM reddit_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM reddit_table")
    fun getAll(): PagingSource<Int, RedditEntity>
}