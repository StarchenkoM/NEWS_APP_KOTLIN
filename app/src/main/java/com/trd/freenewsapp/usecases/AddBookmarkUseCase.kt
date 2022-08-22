package com.trd.freenewsapp.usecases

import com.trd.freenewsapp.homescreen.adapters.NewsItem
import com.trd.freenewsapp.states.BookmarksState

interface AddBookmarkUseCase {
    suspend fun addBookmark(newsItem: NewsItem): BookmarksState
}