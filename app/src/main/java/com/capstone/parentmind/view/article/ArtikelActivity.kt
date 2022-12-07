package com.capstone.parentmind.view.article
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.parentmind.R
import com.capstone.parentmind.databinding.ActivityArtikelBinding
import com.capstone.parentmind.utils.MainViewModel
import com.capstone.parentmind.utils.Result
import com.capstone.parentmind.utils.ViewModelFactory
import com.capstone.parentmind.view.adapter.ArtikelAdapter

class ArtikelActivity : AppCompatActivity() {

    private var layoutManager: RecyclerView.LayoutManager?=null
    private lateinit var binding: ActivityArtikelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArtikelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getArticle()
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

    private fun getArticle() {
        val factory: ViewModelFactory = ViewModelFactory.getInstance(baseContext)
        val mainViewModel: MainViewModel by viewModels {
            factory
        }

        val adapterArticle = ArtikelAdapter { article ->
            if (article.bookmark){
                mainViewModel.saveArticle(article)
            } else {
                mainViewModel.deleteArticle(article)
            }
        }

        mainViewModel.getAllArticles().observe(this, { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        adapterArticle.submitList(result.data)
                        Glide.with(this)
                            .load(result.data[0].thumbnail)
                            .into(binding.ivArtikel)
                        binding.ivArtikel.clipToOutline = true
                        binding.tvDetailArtikel.text = result.data[0].title
                    }
                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        showError(result.error)
                    }
                }
            }
        })

        binding.rvListAhliParenting.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = adapterArticle
        }
    }

    private fun showError(error: String) {
        Toast.makeText(
            this,
            error,
            Toast.LENGTH_SHORT
        ).show()
    }

    companion object {
        private var instance: ArtikelActivity? = null
        private const val TAG = "ArtikelActivity"

        val context: Context?
            get() = instance
    }
}