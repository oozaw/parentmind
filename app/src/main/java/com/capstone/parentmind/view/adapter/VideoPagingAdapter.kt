package com.capstone.parentmind.view.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.parentmind.data.remote.response.ArticlesItem
import com.capstone.parentmind.databinding.ItemListVideoBinding
import com.capstone.parentmind.view.article.detail.DetailArticleActivity
import com.capstone.parentmind.view.video.detail.DetailVideoActivity

class VideoPagingAdapter(private val isMix: Boolean = false) : PagingDataAdapter<ArticlesItem, VideoPagingAdapter.ViewHolder>(DIFF_CALLBACK) {

    class ViewHolder(var binding: ItemListVideoBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemListVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        if (position == itemCount) {
//            holder.itemView.rootView.visibility = View.GONE
//        } else {
        val item = getItem(position)
        item?.let {
            Glide.with(holder.binding.ivVideoEdukasi.context)
                .load(it.thumbnail)
                .into(holder.binding.ivVideoEdukasi)
            holder.binding.tvJudulVideoEdukasi.text = it.title
            holder.binding.tvSource.text = it.source
            if (isMix && item.type == "video") {
                holder.binding.ivPlayBackground.visibility = View.VISIBLE
                holder.binding.ivPlayIcon.visibility = View.VISIBLE
            } else {
                holder.binding.ivPlayBackground.visibility = View.GONE
                holder.binding.ivPlayIcon.visibility = View.GONE
            }

            if (isMix && item.type == "article") {
                holder.itemView.setOnClickListener { article ->
                    Intent(article.context, DetailArticleActivity::class.java).also { intent ->
                        intent.putExtra(DetailArticleActivity.EXTRA_ARTICLE, item)
                        article.context.startActivity(intent)
                    }
                }
            } else {
                holder.itemView.setOnClickListener { video ->
                    Intent(video.context, DetailVideoActivity::class.java).also { intent ->
                        intent.putExtra(DetailVideoActivity.EXTRA_VIDEO, item)
                        video.context.startActivity(intent)
                    }
                }
            }
        }
//        }
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