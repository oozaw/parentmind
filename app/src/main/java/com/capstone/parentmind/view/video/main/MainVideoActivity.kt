package com.capstone.parentmind.view.video.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.capstone.parentmind.R
import com.capstone.parentmind.data.remote.response.ArticlesItem
import com.capstone.parentmind.databinding.ActivityMainVideoBinding
import com.capstone.parentmind.view.adapter.LoadingStateAdapter
import com.capstone.parentmind.view.adapter.VideoPagingAdapter
import com.capstone.parentmind.view.search.SearchActivity
import com.capstone.parentmind.view.video.detail.DetailVideoActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainVideoActivity : AppCompatActivity() {
    private var _binding: ActivityMainVideoBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainVideoViewModel by viewModels()

    private lateinit var videoAdapter: VideoPagingAdapter

    private var isSearching = false

    private lateinit var query: String
    private lateinit var gender: String
    private lateinit var category: String
    private lateinit var type: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_ParentMind)
        _binding = ActivityMainVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        isSearching = intent.getBooleanExtra(SearchActivity.EXTRA_SEARCH, false)

        query = intent.getStringExtra(SearchActivity.EXTRA_QUERY) ?: ""
        gender = intent.getStringExtra(SearchActivity.EXTRA_GENDER) ?: ""
        category = intent.getStringExtra(SearchActivity.EXTRA_CATEGORY) ?: ""
        type = intent.getStringExtra(SearchActivity.EXTRA_TYPE) ?: ""

        videoAdapter = if (isSearching && type == "") {
            VideoPagingAdapter(true)
        } else {
            VideoPagingAdapter()
        }

        setupView()
        setupAction()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_navbar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_search_bar->{
            }
            R.id.menu_notification->{
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupView() {
        if (isSearching) {
            if (query != "") {
                binding.tvSearchPlaceholder.text = query
                binding.tvSearchPlaceholder.setTextColor(ContextCompat.getColor(baseContext, R.color.black))
            } else if (type != "video") {
                binding.tvSearchPlaceholder.text = resources.getString(R.string.cari_artikel_atau_video)
            }

            binding.btnShowVideoSearch.visibility = View.VISIBLE
            if (type != "video") {
                binding.btnShowVideoSearch.text = resources.getString(R.string.show_artikel)
                binding.lnBtnVideo.visibility = View.GONE
            }

            binding.cvLatestVideo.visibility = View.GONE
            getSearchedVideoData()
        } else {
            getVideoData()
        }
    }

    private fun setupAction() {
        binding.btnBackToolbar.setOnClickListener {
            @Suppress("DEPRECATION")
            onBackPressed()
        }

        binding.ivSearch.setOnClickListener {
            if (isSearching) {
                @Suppress("DEPRECATION")
                onBackPressed()
            } else {
                Intent(this, SearchActivity::class.java).also { intent ->
                    startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle())
                }
            }
        }
    }

    private fun getVideoData() {
        viewModel.videoPaging().observe(this) { data ->
            lifecycleScope.launchWhenCreated {
                launch(Dispatchers.Main) {
                    videoAdapter.loadStateFlow.collectLatest {
                        if (it.refresh is LoadState.Loading) {
                            showLoading(true)
                        } else {
//                            videoAdapter.notifyItemRemoved(0)
//                            videoAdapter.notifyItemRangeChanged(1, videoAdapter.itemCount)
                            val latest = videoAdapter.snapshot().items[0]
                            setupLatestVideo(latest)
                            showLoading(false)
                        }
                    }
                }
                setupRecyclerView(data)
            }
        }
    }

    private fun getSearchedVideoData() {
        viewModel.getSearchVideo(query, gender, type, category).observe(this) { data ->
            lifecycleScope.launchWhenCreated {
                launch(Dispatchers.Main) {
                    videoAdapter.loadStateFlow.collectLatest {
                        if (it.refresh is LoadState.Loading) {
                            showLoading(true)
                        } else {
                            if (videoAdapter.itemCount == 0) {
                                showEmptyMessage(true)
                            } else {
                                showEmptyMessage(false)
                            }
                            showLoading(false)
                        }
                    }
                }
                setupRecyclerView(data)
            }
        }
    }

    private fun setupRecyclerView(data: PagingData<ArticlesItem>) {
        videoAdapter.submitData(lifecycle, data)

        binding.rvListVideo.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = videoAdapter
                .withLoadStateHeaderAndFooter(
                footer = LoadingStateAdapter {
                    videoAdapter.retry()
                },
                header = LoadingStateAdapter {
                    videoAdapter.retry()
                }
            )
        }
    }

    private fun setupLatestVideo(video: ArticlesItem) {
        Glide.with(binding.ivLatestVideo.context)
            .load(video.thumbnail)
            .into(binding.ivLatestVideo)
        binding.tvTitleLatestVideo.text = video.title
        binding.tvSourceLatestVideo.text = video.source
        binding.cvLatestVideo.setOnClickListener {
            Intent(this, DetailVideoActivity::class.java).also { intent ->
                intent.putExtra(DetailVideoActivity.EXTRA_VIDEO, video)
                startActivity(intent)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.viewLoading.root.visibility = View.VISIBLE
            binding.viewLoading.backgroundLoading.alpha = 1f
        } else {
            binding.viewLoading.root.visibility = View.GONE
        }
    }

    private fun showEmptyMessage(isEmpty: Boolean) {
        if (isEmpty) {
            binding.viewEmpty.root.visibility = View.VISIBLE
        } else {
            binding.viewEmpty.root.visibility = View.GONE
        }
    }
}