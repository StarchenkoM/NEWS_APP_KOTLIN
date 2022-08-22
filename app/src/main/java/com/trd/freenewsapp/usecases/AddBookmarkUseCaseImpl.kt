package com.trd.freenewsapp.usecases

import com.trd.freenewsapp.homescreen.adapters.NewsItem
import com.trd.freenewsapp.repository.BookmarksRepository
import com.trd.freenewsapp.states.BookmarksState

class AddBookmarkUseCaseImpl(private val repository: BookmarksRepository) : AddBookmarkUseCase {
    override suspend fun addBookmark(newsItem: NewsItem): BookmarksState {
        return repository.addBookmark(newsItem)
    }
}