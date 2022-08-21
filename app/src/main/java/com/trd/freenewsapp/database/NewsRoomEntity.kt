package com.trd.freenewsapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.trd.freenewsapp.data.ArticleSource

@Entity(tableName = "News_table")
data class NewsRoomEntity(
    @PrimaryKey
    var title: String = "",
    val description: String = "",
    val articleUrl: String = "",
    val imageUrl: String = "",
    val sourceUrl: String = "",
)