package com.example.redditrssreader.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.redditrssreader.model.ModelConverter
import com.example.redditrssreader.model.RedditEntity
import com.example.redditrssreader.model.RedditModel
import com.example.redditrssreader.room.RedditDatabase
import com.example.redditrssreader.service.RedditService
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class RedditRemoteMediator(
    private val service: RedditService,
    private val database: RedditDatabase
) : RemoteMediator<Int, RedditEntity>() {
    companion object {
        var lastItemId: String? = null
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, RedditEntity>
    ): MediatorResult {
        try {
            val pageId = when (loadType) {
                LoadType.APPEND -> lastItemId
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = false)
                LoadType.REFRESH -> null

            }

            val result = service.getNextPage(
                when (loadType) {
                    LoadType.REFRESH -> state.config.initialLoadSize
                    else -> state.config.pageSize
                },
                pageId
            ).newsList

            lastItemId = result.last().id

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.redditDao().deleteAll()
                }
                database.redditDao()
                    .insertAll(
                        ModelConverter.convert(
                            result.sortedByDescending(RedditModel::date)
                        )
                    )
            }

            return MediatorResult.Success(endOfPaginationReached = result.isEmpty())
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }
}
