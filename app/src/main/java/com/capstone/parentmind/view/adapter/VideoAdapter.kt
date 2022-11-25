package com.capstone.parentmind.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.capstone.parentmind.R

class VideoAdapter : RecyclerView.Adapter<VideoAdapter.ViewHolder>() {
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
//ini untuk nyoba hasil recycle view
        val list_video: WebView = itemView.findViewById(R.id.wv_video_edukasi)
        val title_video: TextView = itemView.findViewById(R.id.tv_judul_video_edukasi)
    }

    private val list_video = arrayOf(
        "https://www.youtube.com/watch?v=BmTn6K5KaXI",
        "https://www.youtube.com/watch?v=BmTn6K5KaXI",
        "https://www.youtube.com/watch?v=BmTn6K5KaXI",
        "https://www.youtube.com/watch?v=BmTn6K5KaXI",
        "https://www.youtube.com/watch?v=BmTn6K5KaXI"
    )

    private val title_video = arrayOf(
        "Tahap Perkembangan Anak Umur 3 Tahun | Rolen Ria dan Psikolog Pritta Tyas",
        "Tahap Perkembangan Anak Umur 3 Tahun | Rolen Ria dan Psikolog Pritta Tyas",
        "Tahap Perkembangan Anak Umur 3 Tahun | Rolen Ria dan Psikolog Pritta Tyas",
        "Tahap Perkembangan Anak Umur 3 Tahun | Rolen Ria dan Psikolog Pritta Tyas",
        "Tahap Perkembangan Anak Umur 3 Tahun | Rolen Ria dan Psikolog Pritta Tyas"
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_video, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.list_video.loadUrl(list_video[position])
        holder.title_video.text = title_video[position]

    }

    override fun getItemCount(): Int {
        return title_video.size
    }


}