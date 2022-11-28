package com.capstone.parentmind.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.capstone.parentmind.R

class ArtikelAdapter(private val listener: OnAdapterListener) : RecyclerView.Adapter<ArtikelAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val list_artikel: WebView = itemView.findViewById(R.id.wv_artikel)
        val title_artikel: TextView = itemView.findViewById(R.id.tv_detail_judul_artikel)
    }

    private val list_artikel = arrayOf(
        "https://hamil.co.id/anak/parenting/cara-mendidik-anak-usia-3-tahun",
        "https://www.sehatq.com/artikel/perkembangan-fisik-laki-laki-dan-perempuan",
        "https://www.kompasiana.com/riskaoktavianaputripriyani5853/6382eb5908a8b50b0f696ee2/peran-ibu-dalam-pembentukan-karakter-sang-anak",
        "https://www.fimela.com/parenting/read/5120637/orang-tua-sering-memarahi-anak-saat-belajar-kenali-dampak-negatifnya",
        "https://www.fimela.com/parenting/read/5115130/manfaat-memberi-reward-ke-anak-bisa-membentuk-perilaku-baiknya"
    )

    private val title_artikel = arrayOf(
        "9 Cara Mendidik Anak Usia 3 Tahun Agar Tumbuh Maksimal",
        "Perkembangan Fisik Laki-laki dan Perempuan saat Pubertas",
        "Peran Ibu dalam Pembentukan Karakter Sang Anak",
        "Orang Tua Sering Memarahi Anak Saat Belajar, Kenali Dampak Negatifnya",
        "Manfaat Memberi Reward ke Anak, Bisa Membentuk Perilaku Baiknya"
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_ahli_parenting, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.list_artikel.loadUrl(list_artikel[position])
        holder.title_artikel.text = title_artikel[position]

    }

    override fun getItemCount(): Int {
        return title_artikel.size
    }
    interface OnAdapterListener{
        fun onClick()
    }

}