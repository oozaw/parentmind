package com.capstone.parentmind.data.local.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "article")
data class ArticleEntity(
    @PrimaryKey
    val id: Int,

    val thumbnail: String,

    val updatedAt: String,

    val link: String,

    val createdAt: String,

    val source: String,

    val type: String,

    val title: String,

    val excerpt: String,

    val body: String,

    val category: String,

    val authors: String
)
