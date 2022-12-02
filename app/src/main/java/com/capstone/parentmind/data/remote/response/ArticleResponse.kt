package com.capstone.parentmind.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArticleResponse(
   @field:SerializedName("status")
   val status: Int,

   @field:SerializedName("message")
   val message: String,

   @field:SerializedName("listArticle")
   val listArticle: List<ArticleItem>
): Parcelable


@Parcelize
data class ArticleItem(
   @field:SerializedName("thumbnail")
   val thumbnail: String,

   @field:SerializedName("tipe")
   val tipe: String,
): Parcelable