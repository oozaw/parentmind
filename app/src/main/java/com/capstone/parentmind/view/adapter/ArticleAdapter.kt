package com.capstone.parentmind.view.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.parentmind.R
import com.capstone.parentmind.data.local.database.ArticleEntity
import com.capstone.parentmind.databinding.ItemListArticleBinding
import com.capstone.parentmind.view.article.detail.DetailArticleActivity

class ArticleAdapter(private val onBookmarkClick: (ArticleEntity) -> Unit) :
    ListAdapter<ArticleEntity, ArticleAdapter.ViewHolder>(DIFF_CALLBACK) {

    class ViewHolder(var binding: ItemListArticleBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data : ArticleEntity){
            binding.tvTitle.text = data.title
            Glide.with(itemView.context)
                .load(data.thumbnail)
                .into(binding.ivThumbnail)
            binding.ivThumbnail.clipToOutline = true
            itemView.setOnClickListener { moveWithId(data) }
        }

        private fun moveWithId(data: ArticleEntity) {
            val moveWithIdIntent = Intent(itemView.context, DetailArticleActivity::class.java)
//            moveWithIdIntent.putExtra(DetailArticleActivity.EXTRA_ID, id)
//            moveWithIdIntent.putExtra(DetailArticleActivity.EXTRA_ARTICLE, data)
            itemView.context.startActivity(moveWithIdIntent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemListArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        if (data!=null){
            holder.bind(data)
        }
//        val save = holder.binding.ivSave
//        if (data.bookmark){
//            save.setImageDrawable(ContextCompat.getDrawable(save.context, R.drawable.ic_favorite))
//        } else {
//            save.setImageDrawable(ContextCompat.getDrawable(save.context, R.drawable.ic_baseline_favorite_border_24))
//        }
//        save.setOnClickListener{
//            onBookmarkClick(data)
//        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<ArticleEntity> =
            object : DiffUtil.ItemCallback<ArticleEntity>() {
                override fun areItemsTheSame(oldArticle: ArticleEntity, newArticle: ArticleEntity): Boolean {
                    return oldArticle.id == newArticle.id
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldArticle: ArticleEntity, newArticle: ArticleEntity): Boolean {
                    return oldArticle == newArticle
                }
            }
    }

}