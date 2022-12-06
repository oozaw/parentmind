package com.capstone.parentmind.data.remote.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class ArtikelResponse(

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("articles")
	val articles: List<ArticlesItem>,

	@field:SerializedName("status")
	val status: Boolean
) : Parcelable

@Parcelize
data class ArticlesItem(

	@field:SerializedName("thumbnail")
	val thumbnail: String,

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("link")
	val link: String,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("source")
	val source: String,

	@field:SerializedName("type")
	val type: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("excerpt")
	val excerpt: String,

	@field:SerializedName("body")
	val body: String,

	@field:SerializedName("category")
	val category: String,

	@field:SerializedName("authors")
	val authors: String
) : Parcelable
