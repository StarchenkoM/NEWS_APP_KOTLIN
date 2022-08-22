package com.trd.freenewsapp.usecases

import com.trd.freenewsapp.repository.BookmarksRepository
import com.trd.freenewsapp.states.BookmarksState

class LoadBookmarksUseCaseImpl(private val repository: BookmarksRepository) : LoadBookmarksUseCase {
    override suspend fun loadBookmarks(): BookmarksState {
        return repository.loadBookmarks()
    }

}