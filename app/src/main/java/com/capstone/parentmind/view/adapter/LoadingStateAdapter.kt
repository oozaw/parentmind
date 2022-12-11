package com.capstone.parentmind.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.capstone.parentmind.databinding.ItemLoadingPagingBinding
import com.capstone.parentmind.databinding.ViewLoadingBinding

class LoadingStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<LoadingStateAdapter.LoadingStateViewHolder>() {
   override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadingStateViewHolder {
      val binding = ItemLoadingPagingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      return LoadingStateViewHolder(binding, retry)
   }

   override fun onBindViewHolder(holder: LoadingStateViewHolder, loadState: LoadState) {
      holder.bind(loadState)
   }

   class LoadingStateViewHolder(private val binding: ItemLoadingPagingBinding, retry: () -> Unit) :
      RecyclerView.ViewHolder(binding.root) {
      init {
         binding.retryButton.setOnClickListener { retry.invoke() }
      }
      fun bind(loadState: LoadState) {
         if (loadState is LoadState.Error) {
            binding.errorMsg.text = loadState.error.localizedMessage
            binding.errorMsg.isVisible = false
//            binding.retryButton.isVisible = !loadState.endOfPaginationReached
//            binding.errorMsg.isVisible = !loadState.endOfPaginationReached
         }
         binding.retryButton.isVisible = false
         binding.progressBar.isVisible = loadState is LoadState.Loading
      }
   }
}