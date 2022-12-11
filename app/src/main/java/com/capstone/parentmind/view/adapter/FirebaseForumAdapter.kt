package com.capstone.parentmind.view.adapter

import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.parentmind.R
import com.capstone.parentmind.model.ForumMessage
import com.capstone.parentmind.databinding.ItemListForumBinding
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class FirebaseForumAdapter(
   options: FirebaseRecyclerOptions<ForumMessage>,
   private val currentUID: String
): FirebaseRecyclerAdapter<ForumMessage, FirebaseForumAdapter.MessageViewHolder>(options) {

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
      val inflater = LayoutInflater.from(parent.context)
      val view = inflater.inflate(R.layout.item_list_forum, parent, false)
      val binding = ItemListForumBinding.bind(view)
      return MessageViewHolder(binding)
   }

   override fun onBindViewHolder(holder: MessageViewHolder, position: Int, model: ForumMessage) {
      holder.bind(model)
   }

   inner class MessageViewHolder(private val binding: ItemListForumBinding) : RecyclerView.ViewHolder(binding.root) {
      fun bind(item: ForumMessage) {
         binding.tvTitle.text = item.title
         binding.tvUsername.text = if (item.created_by == currentUID) "Saya" else item.name
         Glide.with(itemView.context)
            .load(item.photoUrl)
            .circleCrop()
            .into(binding.ivImageUser)
         if (item.created_at != null) {
            binding.tvTimePosted.text = DateUtils.getRelativeTimeSpanString(item.created_at)
         }
      }
   }
}