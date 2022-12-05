package com.capstone.parentmind.view.video.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.parentmind.R
import com.capstone.parentmind.data.Result
import com.capstone.parentmind.data.remote.response.ArticlesItem
import com.capstone.parentmind.databinding.ActivityMainVideoBinding
import com.capstone.parentmind.utils.makeToast
import com.capstone.parentmind.view.adapter.VideoAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainVideoActivity : AppCompatActivity() {
    private var _binding: ActivityMainVideoBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainVideoViewModel by viewModels()

    private lateinit var videoAdapter: VideoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        videoAdapter = VideoAdapter()

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
        viewModel.getVideos().observe(this) { result ->
            result?.let {
                when (it) {
                    is Result.Loading -> {
                        showLoading(true)
                    }
                    is Result.Success -> {
                        showLoading(false)
                        if (it.data.articles.isNotEmpty()) {
                            Log.d(TAG, "setupView: ${it.data.message}")
                            setupRecyclerView(it.data.articles)
                        } else {
                            makeToast(this, "Data kosong!")
                            Log.e(TAG, "setupView: Data kosong!", )
                        }
                    }
                    is Result.Error -> {
                        showLoading(false)
                        makeToast(this, it.error)
                        Log.e(TAG, "onViewCreated: ${it.error}")
                    }
                }
            }
        }
    }

    private fun setupAction() {
        binding.btnBackToolbar.setOnClickListener {
            @Suppress("DEPRECATION")
            onBackPressed()
        }
    }

    private fun setupRecyclerView(data: List<ArticlesItem>) {
        videoAdapter.submitList(data)

        binding.rvListVideo.apply {
            layoutManager = LinearLayoutManager(context)
            layoutManager = GridLayoutManager(context, 2)
            setHasFixedSize(true)
            adapter = videoAdapter
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

    companion object {
        const val TAG = "MainVideoActivity"
    }
}