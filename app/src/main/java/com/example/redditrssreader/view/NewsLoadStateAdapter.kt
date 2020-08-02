package com.example.redditrssreader.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.redditrssreader.R
import com.example.redditrssreader.databinding.NewsLoadStateItemBinding

class NewsLoadStateAdapter(private val adapter: RedditAdapter) : LoadStateAdapter<NewsLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: NewsLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): NewsLoadStateViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.news_load_state_item, parent, false)
        return NewsLoadStateViewHolder(view) { adapter.retry() }
    }
}

class NewsLoadStateViewHolder(itemView: View, private val retryFun: () -> Unit) : RecyclerView.ViewHolder(itemView) {
    private val binding = NewsLoadStateItemBinding.bind(itemView)

    fun bind(loadState: LoadState) {
        binding.btnRetry.apply {
            isVisible = loadState is LoadState.Error
            setOnClickListener { retryFun() }
        }
        binding.progressLoading.isVisible = loadState is LoadState.Loading
        binding.tvError.apply {
            isVisible = !(loadState as? LoadState.Error)?.error?.message.isNullOrBlank()
            text = (loadState as? LoadState.Error)?.error?.message
        }
    }
}
