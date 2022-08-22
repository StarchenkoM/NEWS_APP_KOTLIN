package com.trd.freenewsapp.states

import com.trd.freenewsapp.homescreen.adapters.NewsItem

sealed class BookmarksState {
    object BookmarksLoading : BookmarksState()
    object BookmarksLoadingError : BookmarksState()
    object BookmarkAdded : BookmarksState()
    object BookmarkRemoved : BookmarksState()
    data class BookmarksLoadingSuccess(val bookmarks: List<NewsItem>) : BookmarksState()
}
