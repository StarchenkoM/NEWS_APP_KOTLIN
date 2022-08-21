package com.trd.freenewsapp.database

import com.trd.freenewsapp.data.ArticleItemApi
import com.trd.freenewsapp.data.ArticleSource
import com.trd.freenewsapp.data.NewsApiResponse
import com.trd.freenewsapp.homescreen.adapter.NewsItem
import javax.inject.Inject

class NewsMapper @Inject constructor() {

    fun mapToDBEntity(articleItemApi: ArticleItemApi) = NewsRoomEntity(
        title = articleItemApi.title,
        description = articleItemApi.description,
        articleUrl = articleItemApi.articleUrl,
        imageUrl = articleItemApi.imageUrl,
        sourceUrl = articleItemApi.source.sourceUrl,
    )
/*        NewsRoomEntity().apply {
            articleItemApi.title
            articleItemApi.description
            articleItemApi.articleUrl
            articleItemApi.imageUrl
            articleItemApi.source.sourceUrl
        }*/

    fun mapToArticleItemApi(dbEntity: NewsRoomEntity) = ArticleItemApi(
        source = ArticleSource(sourceUrl = dbEntity.sourceUrl),
        title = dbEntity.title,
        description = dbEntity.description,
        articleUrl = dbEntity.articleUrl,
        imageUrl = dbEntity.imageUrl,
    )

    fun mapToNewsItem(articleItemApi: ArticleItemApi) = NewsItem(
        title = articleItemApi.title ?: "",
        description = articleItemApi.description ?: "",
        articleUrl = articleItemApi.articleUrl ?: "",
        imageUrl = articleItemApi.imageUrl ?: "",
        sourceUrl = articleItemApi.source.sourceUrl ?: "",
    )
}