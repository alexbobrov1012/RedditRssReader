package com.example.redditrssreader.service

import com.example.redditrssreader.model.RedditFeedModel
import retrofit2.http.GET
import retrofit2.http.Query

interface RedditService {
    @GET(".rss?sort=new")
    suspend fun getNextPage(
        @Query("limit") pageSize: Int,
        @Query("after") id: String?
    ): RedditFeedModel
}