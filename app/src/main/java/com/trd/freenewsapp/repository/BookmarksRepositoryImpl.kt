package com.trd.freenewsapp.repository

import android.util.Log
import com.trd.freenewsapp.constants.Constants
import com.trd.freenewsapp.constants.Constants.LOG_TAG
import com.trd.freenewsapp.database.NewsDao
import com.trd.freenewsapp.database.NewsMapper
import com.trd.freenewsapp.database.NewsRoomEntity
import com.trd.freenewsapp.di.IoCoroutineScope
import com.trd.freenewsapp.homescreen.adapters.NewsItem
import com.trd.freenewsapp.states.BookmarksState
import com.trd.freenewsapp.states.BookmarksState.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class BookmarksRepositoryImpl(
    private val newsMapper: NewsMapper,
    private val newsDao: NewsDao,
    @IoCoroutineScope private val ioCoroutineScope: CoroutineScope,
) : BookmarksRepository {


    override suspend fun loadBookmarks(): BookmarksState {
        return handleBDLoading()
    }

    override suspend fun loadBookmarksByQuery(query: String): BookmarksState {
        return handleDBLoadingByQuery(query)
    }

    override suspend fun addBookmark(newsItem: NewsItem): BookmarksState {
        val bookmark = newsMapper.mapNewsItemToDBItem(newsItem)
        val result = addBookmarkStatus(bookmark)
        return if (result > 0) BookmarkAdded else BookmarksLoadingError
    }


    private suspend fun addBookmarkStatus(bookmark: NewsRoomEntity): Long {
        return ioCoroutineScope.async(Dispatchers.IO) {
            newsDao.insertNewsToDb(bookmark)
        }.await()
    }


    override suspend fun removeBookmark(newsItem: NewsItem): BookmarksState {
        val result = removeBookmarkStatus(newsItem.title)
        val bookmarks = articlesFromDB()
        val res = bookmarks.map { newsMapper.mapDBItemToNewsItem(it) }
        return if (result > 0) BookmarksLoadingSuccess(res) else BookmarksLoadingError
    }

    private suspend fun removeBookmarkStatus(title: String): Int {
        return ioCoroutineScope.async(Dispatchers.IO) {
            newsDao.deleteNewsEntity(title)
        }.await()
    }

    override suspend fun handleBDLoading(): BookmarksState {
        val bookmarks = articlesFromDB()
        Log.i(LOG_TAG, "handleDBLoadingByQuery:BookmarksRepositoryImpl bookmarks = $bookmarks")
        return if (bookmarks.isNullOrEmpty()) {
            BookmarksLoadingError
        } else {
            val result = bookmarks.map { newsMapper.mapDBItemToNewsItem(it) }
            BookmarksLoadingSuccess(result)
        }
    }

    private suspend fun articlesFromDB(): List<NewsRoomEntity> {
        return ioCoroutineScope.async(Dispatchers.IO) {
            newsDao.getAllBookmarksAsList()
        }.await()
    }

    override suspend fun handleDBLoadingByQuery(query: String): BookmarksState {
        val bookmarks = articlesFromDB()
        Log.i(LOG_TAG, "handleDBLoadingByQuery:BookmarksRepositoryImpl bookmarks = $bookmarks")
        return if (bookmarks.isNullOrEmpty()) {
            BookmarksLoadingError
        } else {
            val result = bookmarks.map { newsMapper.mapDBItemToNewsItem(it) }
            BookmarksLoadingSuccess(result)
        }
    }


}