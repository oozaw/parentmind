package com.capstone.parentmind.data.local.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "article")
data class ArticleEntity(
    @PrimaryKey
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("thumbnail")
    val thumbnail: String,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("link")
    val link: String,

    @field:SerializedName("bookmark")
    var bookmark: Boolean
):Parcelable
