package com.capstone.parentmind.view.video.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import com.capstone.parentmind.R
import com.capstone.parentmind.data.remote.response.ArticlesItem
import com.capstone.parentmind.databinding.ActivityMainVideoBinding
import com.capstone.parentmind.view.adapter.LoadingStateAdapter
import com.capstone.parentmind.view.adapter.VideoPagingAdapter
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        videoAdapter = VideoPagingAdapter()

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
        getVideoData()
    }

    private fun setupAction() {
        binding.btnBackToolbar.setOnClickListener {
            @Suppress("DEPRECATION")
            onBackPressed()
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
            adapter = videoAdapter.withLoadStateHeaderAndFooter(
                footer = LoadingStateAdapter {
                    videoAdapter.retry()
                },
                header = LoadingStateAdapter {
                    videoAdapter.retry()
                }
            )
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
}