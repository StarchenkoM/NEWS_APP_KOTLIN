package com.trd.freenewsapp.repository

import android.util.Log
import com.trd.freenewsapp.constants.Constants
import com.trd.freenewsapp.data.NewsApiResponse
import com.trd.freenewsapp.data.api.RetrofitInstance
import com.trd.freenewsapp.database.NewsDao
import com.trd.freenewsapp.database.NewsMapper
import com.trd.freenewsapp.states.NewsState
import com.trd.freenewsapp.states.NewsState.NewsLoadingError
import com.trd.freenewsapp.states.NewsState.NewsLoadingSuccess
import retrofit2.Response

class HomeScreenRepositoryImpl(
    private val newsMapper: NewsMapper,
    private val newsDao: NewsDao,
) : HomeScreenRepository {


    override suspend fun loadNews(): NewsState {
        return handleApiLoading()
    }

    override suspend fun loadNewsByQuery(query: String): NewsState {
        return handleApiLoadingByQuery(query)
    }

    override suspend fun loadNewsByPage(page: Int): NewsState {
        return handleApiLoading(page)
    }

    private suspend fun getNewsFromApi(page: Int = 1): Response<NewsApiResponse> {
        return RetrofitInstance.api.getNews(page = page.toString())
    }

    private suspend fun handleApiLoading(page: Int = 1): NewsState {
        val articlesApi = getNewsFromApi(page).body()?.articles
        val result = articlesApi?.map { newsMapper.mapApiItemToNewsItem(it) }
        return result?.let { NewsLoadingSuccess(it) } ?: NewsLoadingError
    }

    override suspend fun getNewsFromApiByQuery(query: String): Response<NewsApiResponse> {
        return RetrofitInstance.api.getNewsByQuery(query = query)
    }

    private suspend fun handleApiLoadingByQuery(query: String): NewsState {
        val articlesApi = getNewsFromApiByQuery(query).body()?.articles
        val result = articlesApi?.map { newsMapper.mapApiItemToNewsItem(it) }
        return result?.let { NewsLoadingSuccess(it) } ?: NewsLoadingError
    }
}