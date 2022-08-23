package com.trd.freenewsapp.usecases

import com.trd.freenewsapp.homescreen.adapters.NewsItem
import com.trd.freenewsapp.repository.BookmarksRepository
import com.trd.freenewsapp.states.BookmarksState

class RemoveBookmarkUseCaseImpl(private val repository: BookmarksRepository) :
    RemoveBookmarkUseCase {
    override suspend fun removeBookmark(newsItem: NewsItem): BookmarksState {
        return repository.removeBookmark(newsItem)
    }
}