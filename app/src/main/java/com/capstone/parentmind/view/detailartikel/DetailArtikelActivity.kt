package com.capstone.parentmind.view.detailartikel

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.capstone.parentmind.R
import com.capstone.parentmind.data.local.database.ArticleEntity
import com.capstone.parentmind.data.remote.response.Article
import com.capstone.parentmind.data.remote.response.DetailArtikelResponse
import com.capstone.parentmind.databinding.ActivityDetailArtikelBinding
import com.capstone.parentmind.utils.MainViewModel
import com.capstone.parentmind.utils.Result
import com.capstone.parentmind.utils.ViewModelFactory

class DetailArtikelActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailArtikelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailArtikelBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val data = intent.getParcelableExtra<ArticleEntity>(EXTRA_ARTICLE) as ArticleEntity
        getData(data.id)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(baseContext)
        val mainViewModel: MainViewModel by viewModels {
            factory
        }

        changeIcon(data.bookmark)
        binding.btnSaveArtikel.setOnClickListener {
            if (data.bookmark){
                mainViewModel.deleteArticle(data)
            }else {
                mainViewModel.saveArticle(data)
            }
            changeIcon(data.bookmark)
        }
    }

    private fun changeIcon(value: Boolean) {
        if (value){
            binding.btnSaveArtikel.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_favorite, 0, 0, 0)
        }else {
            binding.btnSaveArtikel.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_favorite_border_24, 0, 0, 0)
        }
    }

    private fun getData(id: Int) {
        val factory: ViewModelFactory = ViewModelFactory.getInstance(baseContext)
        val mainViewModel: MainViewModel by viewModels {
            factory
        }

        mainViewModel.getDetailArticles(id).observe(this, {result ->
            if (result != null){
                when (result) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        setData(result.data.article)
                    }
                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        showError(result.error)
                    }
                }
            }
        })
    }

    private fun setData(article: Article) {
        val factory: ViewModelFactory = ViewModelFactory.getInstance(baseContext)
        val mainViewModel: MainViewModel by viewModels {
            factory
        }

        Glide.with(this)
            .load("https://parentmind.my.id/storage/${article.thumbnail}")
            .into(binding.ivThumbnail)
        binding.ivThumbnail.clipToOutline = true
        binding.tvSource.text = article.source
        binding.tvDetailJudulArtikel.text = article.title
        binding.tvSkripArtikel.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(article.body, Html.FROM_HTML_MODE_COMPACT)
        } else {
            Html.fromHtml(article.body)
        }
        binding.tvSource.text = article.source
    }


    private fun showError(error: String) {
        Toast.makeText(
            this,
            error,
            Toast.LENGTH_SHORT
        ).show()
    }

    companion object{
        const val EXTRA_ID = "extra_id"
        const val EXTRA_ARTICLE = "extra_article"
    }
}