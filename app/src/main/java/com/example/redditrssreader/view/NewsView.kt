package com.example.redditrssreader.view

import androidx.paging.PagingData
import com.example.redditrssreader.model.RedditEntity

interface NewsView {
    fun onDataReceived(data: PagingData<RedditEntity>)
}