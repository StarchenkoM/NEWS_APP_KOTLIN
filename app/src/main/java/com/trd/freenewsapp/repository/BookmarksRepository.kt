package com.trd.freenewsapp.repository

import com.trd.freenewsapp.homescreen.adapters.NewsItem
import com.trd.freenewsapp.states.BookmarksState

interface BookmarksRepository {
    suspend fun handleBDLoading(): BookmarksState
    suspend fun loadBookmarks(): BookmarksState
    suspend fun addBookmark(newsItem: NewsItem): BookmarksState
    suspend fun removeBookmark(newsItem: NewsItem): BookmarksState
}