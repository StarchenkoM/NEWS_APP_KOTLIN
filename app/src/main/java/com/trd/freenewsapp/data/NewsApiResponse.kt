package com.trd.freenewsapp.data

import com.google.gson.annotations.SerializedName

data class NewsApiResponse(
    @SerializedName("articles")
    val articles: List<ArticleItemApi>,
)

data class ArticleItemApi(
    @SerializedName("source")
    val source: ArticleSource,
    @SerializedName("title")
    val title: String = "",
    @SerializedName("description")
    val description: String = "",
    @SerializedName("url")
    val articleUrl: String = "",
    @SerializedName("urlToImage")
    val imageUrl: String = "",
)

data class ArticleSource(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("name")
    val sourceUrl: String = "",
)