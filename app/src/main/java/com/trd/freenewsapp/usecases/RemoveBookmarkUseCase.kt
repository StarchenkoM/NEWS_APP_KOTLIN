package com.trd.freenewsapp.usecases

import com.trd.freenewsapp.homescreen.adapters.NewsItem
import com.trd.freenewsapp.states.BookmarksState

interface RemoveBookmarkUseCase {
    suspend fun removeBookmark(newsItem: NewsItem): BookmarksState
}