package com.capstone.parentmind.view.article

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import com.capstone.parentmind.R
import com.capstone.parentmind.data.remote.response.ArticlesItem
import com.capstone.parentmind.databinding.FragmentArticleBinding
import com.capstone.parentmind.view.adapter.LoadingStateAdapter
import com.capstone.parentmind.view.adapter.VideoPagingAdapter
import com.capstone.parentmind.view.article.main.MainArticleActivity
import com.capstone.parentmind.view.article.main.MainArticleViewModel
import com.capstone.parentmind.view.search.SearchActivity
import com.capstone.parentmind.view.video.main.MainVideoActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ArticleFragment : Fragment() {

   private var _binding: FragmentArticleBinding? = null
   private val binding get() = _binding!!

   private val viewModel: MainArticleViewModel by viewModels()

   private lateinit var articleAdapter: VideoPagingAdapter

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View {
      // Inflate the layout for this fragment
      _binding = FragmentArticleBinding.inflate(layoutInflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)

      articleAdapter = VideoPagingAdapter(true)

      setupView()
      setupAction()
   }

   override fun onDestroy() {
      super.onDestroy()
      _binding = null
   }

   private fun setupView() {
      getArticleData()
   }

   private fun setupAction() {
      binding.cvArticle.setOnClickListener {
         Intent(requireActivity(), MainArticleActivity::class.java).also { intent ->
            startActivity(intent)
         }
      }

      binding.cvVideo.setOnClickListener {
         Intent(requireActivity(), MainVideoActivity::class.java).also { intent ->
            startActivity(intent)
         }
      }

      binding.btnSearch.setOnClickListener {
         Intent(requireActivity(), SearchActivity::class.java).also { intent ->
            startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(requireActivity()).toBundle())
         }
      }
   }

   private fun getArticleData() {
      viewModel.getAllArticles().observe(viewLifecycleOwner) { data ->
         lifecycleScope.launchWhenCreated {
            launch(Dispatchers.Main) {
               articleAdapter.loadStateFlow.collectLatest {
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
      articleAdapter.submitData(lifecycle, data)

      binding.rvListArticle.apply {
         layoutManager = GridLayoutManager(context, 2)
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

   private fun showLoading(isLoading: Boolean) {
      if (isLoading) {
         binding.viewLoading.root.visibility = View.VISIBLE
         binding.viewLoading.backgroundLoading.alpha = 1f
      } else {
         binding.viewLoading.root.visibility = View.GONE
      }
   }
}