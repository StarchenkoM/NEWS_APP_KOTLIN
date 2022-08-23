package com.trd.freenewsapp.repository

import com.trd.freenewsapp.data.NewsApiResponse
import com.trd.freenewsapp.states.NewsState
import retrofit2.Response

interface HomeScreenRepository {
//    suspend fun getNewsFromApi(page: Int = 1 ): Response<NewsApiResponse>
    suspend fun getNewsFromApiByQuery(query: String): Response<NewsApiResponse>
    suspend fun loadNews(): NewsState
    suspend fun loadNewsByQuery(query: String): NewsState
    suspend fun loadNewsByPage(page: Int): NewsState
}