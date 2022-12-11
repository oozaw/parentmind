package com.capstone.parentmind.view.article.main

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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.capstone.parentmind.R
import com.capstone.parentmind.data.remote.response.ArticlesItem
import com.capstone.parentmind.databinding.ActivityMainArticleBinding
import com.capstone.parentmind.view.adapter.ArticlePagingAdapter
import com.capstone.parentmind.view.adapter.LoadingStateAdapter
import com.capstone.parentmind.view.adapter.VideoPagingAdapter
import com.capstone.parentmind.view.article.detail.DetailArticleActivity
import com.capstone.parentmind.view.search.SearchActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainArticleActivity : AppCompatActivity() {

    private var _binding: ActivityMainArticleBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainArticleViewModel by viewModels()

    private lateinit var articleAdapter: ArticlePagingAdapter

    private var isSearching = false

    private lateinit var query: String
    private lateinit var gender: String
    private lateinit var category: String
    private lateinit var type: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_ParentMind)
        _binding = ActivityMainArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        isSearching = intent.getBooleanExtra(SearchActivity.EXTRA_SEARCH, false)

        query = intent.getStringExtra(SearchActivity.EXTRA_QUERY) ?: ""
        gender = intent.getStringExtra(SearchActivity.EXTRA_GENDER) ?: ""
        category = intent.getStringExtra(SearchActivity.EXTRA_CATEGORY) ?: ""
        type = intent.getStringExtra(SearchActivity.EXTRA_TYPE) ?: "article"

        articleAdapter = ArticlePagingAdapter()

        setupView()

        setupAction()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    //option main menu
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
            }

            binding.btnShowVideoSearch.visibility = View.VISIBLE
            binding.cvLatestArticle.visibility = View.GONE
            getSearchedArticleData()
        } else {
            getArticleData()
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

    private fun getArticleData() {
        viewModel.getArticles("article").observe(this) { data ->
            lifecycleScope.launchWhenCreated {
                launch(Dispatchers.Main) {
                    articleAdapter.loadStateFlow.collectLatest {
                        if (it.refresh is LoadState.Loading) {
                            showLoading(true)
                        } else {
                            val latest = articleAdapter.snapshot().items[0]
                            setupLatestArticle(latest)
                            showLoading(false)
                        }
                    }
                }
                setupRecyclerView(data)
            }
        }
    }

    private fun getSearchedArticleData() {
        viewModel.getSearchArticles(query, gender, type, category).observe(this) { data ->
            lifecycleScope.launchWhenCreated {
                launch(Dispatchers.Main) {
                    articleAdapter.loadStateFlow.collectLatest {
                        if (it.refresh is LoadState.Loading) {
                            showLoading(true)
                        } else {
                            if (articleAdapter.itemCount == 0) {
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
        articleAdapter.submitData(lifecycle, data)

        binding.rvListArticle.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = articleAdapter.withLoadStateHeaderAndFooter(
                footer = LoadingStateAdapter {
                    articleAdapter.retry()
                },
                header = LoadingStateAdapter {
                    articleAdapter.retry()
                }
            )
        }
    }

    private fun setupLatestArticle(article: ArticlesItem) {
        Glide.with(binding.ivLatestArticle.context)
            .load(article.thumbnail)
            .into(binding.ivLatestArticle)
        binding.tvTitleLatestArticle.text = article.title
        binding.tvSourceLatestArticle.text = article.source
        binding.cvLatestArticle.setOnClickListener {
            Intent(this, DetailArticleActivity::class.java).also { intent ->
                intent.putExtra(DetailArticleActivity.EXTRA_ARTICLE, article)
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