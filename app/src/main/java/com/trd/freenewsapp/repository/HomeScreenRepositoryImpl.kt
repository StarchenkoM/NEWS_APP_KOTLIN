package com.trd.freenewsapp.repository

import android.util.Log
import com.trd.freenewsapp.constants.Constants
import com.trd.freenewsapp.data.NewsApiResponse
import com.trd.freenewsapp.data.api.RetrofitInstance
import com.trd.freenewsapp.database.NewsDao
import com.trd.freenewsapp.database.NewsMapper
import com.trd.freenewsapp.homescreen.NewsState
import com.trd.freenewsapp.homescreen.NewsState.NewsLoadingError
import com.trd.freenewsapp.homescreen.NewsState.NewsLoadingSuccess
import retrofit2.Response

class HomeScreenRepositoryImpl(
    private val newsMapper: NewsMapper,
    private val newsDao: NewsDao,
) : HomeScreenRepository {


    override suspend fun loadNews(): NewsState {
        return handleApiLoading()
    }

    override suspend fun getNewsFromApi(): Response<NewsApiResponse> {
        return RetrofitInstance.api.getNews()
    }

    private suspend fun handleApiLoading(): NewsState {
        val articlesApi = getNewsFromApi().body()?.articles
        Log.i(Constants.LOG_TAG, "handleApiLoading:HomeScreenRepositoryImpl articlesApi = $articlesApi")
        val result = articlesApi?.map { newsMapper.mapToNewsItem(it) }
//        articlesApi?.let { putArticlesToDB(it) }
        return result?.let { NewsLoadingSuccess(it) } ?: NewsLoadingError
    }
}