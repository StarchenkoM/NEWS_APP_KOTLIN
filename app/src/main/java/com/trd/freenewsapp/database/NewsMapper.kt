package com.trd.freenewsapp.database

import com.trd.freenewsapp.data.ArticleItemApi
import com.trd.freenewsapp.homescreen.adapters.NewsItem
import javax.inject.Inject

class NewsMapper @Inject constructor() {

    fun mapApiItemToDBEntity(articleItemApi: ArticleItemApi) = NewsRoomEntity(
        title = articleItemApi.title,
        description = articleItemApi.description,
        articleUrl = articleItemApi.articleUrl,
        imageUrl = articleItemApi.imageUrl,
        sourceUrl = articleItemApi.source.sourceUrl,
    )

    fun mapApiItemToNewsItem(articleItemApi: ArticleItemApi) = NewsItem(
        title = articleItemApi.title ?: "",
        description = articleItemApi.description ?: "",
        articleUrl = articleItemApi.articleUrl ?: "",
        imageUrl = articleItemApi.imageUrl ?: "",
        sourceUrl = articleItemApi.source.sourceUrl ?: "",
    )

    fun mapDBItemToNewsItem(articleItemDB: NewsRoomEntity) = NewsItem(
        title = articleItemDB.title ?: "",
        description = articleItemDB.description ?: "",
        articleUrl = articleItemDB.articleUrl ?: "",
        imageUrl = articleItemDB.imageUrl ?: "",
        sourceUrl = articleItemDB.sourceUrl ?: "",
    )

    fun mapNewsItemToDBItem(newsItem: NewsItem) = NewsRoomEntity(
        title = newsItem.title ?: "",
        description = newsItem.description ?: "",
        articleUrl = newsItem.articleUrl ?: "",
        imageUrl = newsItem.imageUrl ?: "",
        sourceUrl = newsItem.sourceUrl ?: "",
    )
}