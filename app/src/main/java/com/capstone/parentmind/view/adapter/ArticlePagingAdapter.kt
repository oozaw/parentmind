package com.capstone.parentmind.view.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.parentmind.data.remote.response.ArticlesItem
import com.capstone.parentmind.databinding.ItemListArticleBinding
import com.capstone.parentmind.view.article.detail.DetailArticleActivity
import com.capstone.parentmind.view.video.detail.DetailVideoActivity

class ArticlePagingAdapter : PagingDataAdapter<ArticlesItem, ArticlePagingAdapter.ViewHolder>(DIFF_CALLBACK) {

    class ViewHolder(var binding: ItemListArticleBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemListArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position == 0) {
            holder.itemView.rootView.layoutParams = LinearLayout.LayoutParams(0, 0)
        } else {
            val article = getItem(position)
            article?.let { item ->
                Glide.with(holder.binding.ivThumbnail.context)
                    .load(item.thumbnail)
                    .into(holder.binding.ivThumbnail)
                holder.binding.tvTitle.text = item.title
                holder.binding.tvSource.text = article.source
                holder.binding.ivOpen.setOnClickListener {
                    Intent(Intent.ACTION_VIEW, Uri.parse(item.link)).also { intent ->
                        holder.itemView.context.startActivity(intent)
                    }
                }
            }
            holder.itemView.setOnClickListener { item ->
                Intent(item.context, DetailArticleActivity::class.java).also { intent ->
                    intent.putExtra(DetailArticleActivity.EXTRA_ARTICLE, article)
                    item.context.startActivity(intent)
                }
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