package com.capstone.parentmind.view.article.detail

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import com.bumptech.glide.Glide
import com.capstone.parentmind.R
import com.capstone.parentmind.data.remote.response.ArticlesItem
import com.capstone.parentmind.databinding.ActivityDetailArticleBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailArticleActivity : AppCompatActivity() {

    private var _binding: ActivityDetailArticleBinding? = null
    private val binding get() = _binding!!

    private lateinit var article: ArticlesItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_ParentMind)
        _binding = ActivityDetailArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        @Suppress("DEPRECATION")
        intent.getParcelableExtra<ArticlesItem>(EXTRA_ARTICLE)?.let { article = it }

        setupView()
        setupAction()
    }

    private fun setupView() {
        Glide.with(this)
            .load(article.thumbnail)
            .into(binding.ivThumbnail)
        binding.ivThumbnail.clipToOutline = true
        binding.tvSource.text = article.source
        binding.tvDetailJudulArtikel.text = article.title
        binding.tvSkripArtikel.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(article.body, Html.FROM_HTML_MODE_COMPACT)
        } else {
            @Suppress("DEPRECATION")
            Html.fromHtml(article.body)
        }
        binding.tvSource.text = article.source
    }

    private fun setupAction() {
        binding.btnBackToolbar.setOnClickListener {
            @Suppress("DEPRECATION")
            onBackPressed()
        }

        binding.btnOpenArticle.setOnClickListener {
            Intent(Intent.ACTION_VIEW, Uri.parse(article.link)).also { intent ->
                startActivity(intent)
            }
        }
    }

    companion object{
        const val EXTRA_ARTICLE = "article"
    }
}