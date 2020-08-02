package com.example.redditrssreader.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.RecyclerView
import com.example.redditrssreader.RssReaderApplication
import com.example.redditrssreader.databinding.ActivityRedditNewsBinding
import com.example.redditrssreader.model.RedditEntity
import com.example.redditrssreader.presenter.RedditPresenter
import kotlinx.android.synthetic.main.activity_reddit_news.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class RedditNewsActivity : AppCompatActivity(), NewsView {
    @Inject
    lateinit var presenter: RedditPresenter<RedditNewsActivity>

    private lateinit var binding: ActivityRedditNewsBinding
    private lateinit var adapter: RedditAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRedditNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpAdapter()
        setUpPresenter()
        setUpSwipeRefresh()
    }

    override fun onDataReceived(data: PagingData<RedditEntity>) {
        lifecycleScope.launch {
            adapter.submitData(data)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detach()
    }

    private fun setUpAdapter() {
        adapter = RedditAdapter()
        binding.recyclerViewList.adapter = adapter.withLoadStateFooter(
            footer = NewsLoadStateAdapter(adapter)
        )
        adapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    private fun setUpPresenter() {
        (application as RssReaderApplication).presenterComponent.inject(this)
        presenter.attach(this)
        lifecycleScope.launch {
            @OptIn(ExperimentalCoroutinesApi::class)
            presenter.getNews()
        }
    }

    private fun setUpSwipeRefresh() {
        lifecycleScope.launch {
            @OptIn(ExperimentalCoroutinesApi::class)
            adapter.loadStateFlow.collectLatest { state ->
                swipe_refresh.isRefreshing = state.refresh is LoadState.Loading
            }
        }
        swipe_refresh.setOnRefreshListener {
            adapter.refresh()
        }
    }
}
