package com.example.redditrssreader

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.RecyclerView
import com.example.redditrssreader.databinding.ActivityMainBinding
import com.example.redditrssreader.model.RedditEntity
import com.example.redditrssreader.presenter.RedditPresenter
import com.example.redditrssreader.view.NewsLoadStateAdapter
import com.example.redditrssreader.view.NewsView
import com.example.redditrssreader.view.RedditAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity(), NewsView {
    @Inject
    lateinit var presenter: RedditPresenter<MainActivity>

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: RedditAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
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
