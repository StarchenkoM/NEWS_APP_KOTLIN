package com.trd.freenewsapp.usecases

import com.trd.freenewsapp.states.BookmarksState

interface LoadBookmarksUseCase {
    suspend fun loadBookmarks(): BookmarksState
}