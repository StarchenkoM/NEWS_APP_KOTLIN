package com.trd.freenewsapp.data.api


import com.trd.freenewsapp.constants.Constants.API_KEY
import com.trd.freenewsapp.data.NewsApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("v2/top-headlines")
    suspend fun getNews(
        @Query("category") category: String = DEFAULT_CATEGORY,
        @Query("pageSize") pageSize: String = DEFAULT_PAGE_SIZE,
        @Query("page") page: String = DEFAULT_PAGE,
        @Query("apiKey") apiKey: String = API_KEY,
    ): Response<NewsApiResponse>

    companion object {
        const val DEFAULT_CATEGORY = "general"
        const val DEFAULT_PAGE_SIZE = "10"
        const val DEFAULT_PAGE = "1"
    }

}