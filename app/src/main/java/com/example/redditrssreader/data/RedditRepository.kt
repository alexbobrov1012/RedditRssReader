package com.example.redditrssreader.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.redditrssreader.model.RedditEntity
import com.example.redditrssreader.room.RedditDatabase
import com.example.redditrssreader.service.RedditService
import kotlinx.coroutines.flow.Flow

class RedditRepository(private val database: RedditDatabase, private val service: RedditService) {
    companion object {
        private const val NETWORK_PAGE_SIZE = 30
    }
    fun getNews(): Flow<PagingData<RedditEntity>> {
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE),
            remoteMediator = RedditRemoteMediator(
                service,
                database
            ),
            pagingSourceFactory = {database.redditDao().getAll() }
        ).flow
    }
}