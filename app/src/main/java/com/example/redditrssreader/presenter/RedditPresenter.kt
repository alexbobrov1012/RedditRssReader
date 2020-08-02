package com.example.redditrssreader.presenter

import com.example.redditrssreader.data.RedditRepository
import com.example.redditrssreader.view.NewsView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest

class RedditPresenter<V: NewsView>(private val repository: RedditRepository) {
    private var view: V? = null

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    suspend fun getNews() {
        repository.getNews().collectLatest { data->
            view?.onDataReceived(data)
        }
    }

    fun attach(view: V) {
        this.view = view
    }

    fun detach() {
        this.view = null
    }
}