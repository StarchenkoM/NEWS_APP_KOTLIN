package com.trd.freenewsapp.repository

import com.trd.freenewsapp.data.NewsApiResponse
import com.trd.freenewsapp.homescreen.NewsState
import retrofit2.Response

interface HomeScreenRepository {
    suspend fun getNewsFromApi(): Response<NewsApiResponse>
    suspend fun loadNews(): NewsState
}