package com.trd.freenewsapp.listeners

import com.trd.freenewsapp.homescreen.adapters.NewsItem

interface BookmarkButtonListener {
    fun addBookmark(newsItem: NewsItem)
    fun removeBookmark(newsItem: NewsItem)
}