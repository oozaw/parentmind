package com.capstone.parentmind.view.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.parentmind.R
import com.capstone.parentmind.data.remote.response.ArticlesItem
import com.capstone.parentmind.databinding.ItemListArtikelBinding
import com.capstone.parentmind.databinding.ItemListVideoBinding
import com.capstone.parentmind.view.video.detail.DetailVideoActivity

class VideoAdapter : ListAdapter<ArticlesItem, VideoAdapter.ViewHolder>(DIFF_CALLBACK) {

    class ViewHolder(var binding: ItemListVideoBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemListVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val video = getItem(position)
        video?.let { vid ->
            Glide.with(holder.binding.ivVideoEdukasi.context)
                .load(vid.thumbnail)
                .into(holder.binding.ivVideoEdukasi)
            holder.binding.tvJudulVideoEdukasi.text = vid.title
            holder.binding.tvSource.text = vid.source
        }
        holder.itemView.setOnClickListener { item ->
            Intent(item.context, DetailVideoActivity::class.java).also { intent ->
                intent.putExtra(DetailVideoActivity.EXTRA_VIDEO, video)
                item.context.startActivity(intent)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<ArticlesItem> =
            object: DiffUtil.ItemCallback<ArticlesItem>() {
                override fun areItemsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
                    return oldItem.id == newItem.id
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
                    return oldItem == newItem
                }
            }
    }
}