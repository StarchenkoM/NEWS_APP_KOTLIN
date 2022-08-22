package com.trd.freenewsapp.homescreen

import com.trd.freenewsapp.homescreen.adapters.NewsItem

sealed class NewsState {
    object NewsLoading : NewsState()
    object NewsLoadingError : NewsState()
    data class NewsLoadingSuccess(val newsItems: List<NewsItem>) : NewsState()
}
