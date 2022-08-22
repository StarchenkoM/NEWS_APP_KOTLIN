package com.trd.freenewsapp.homescreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trd.freenewsapp.constants.Constants.LOG_TAG
import com.trd.freenewsapp.homescreen.adapters.NewsItem
import com.trd.freenewsapp.states.BookmarksState
import com.trd.freenewsapp.states.BookmarksState.BookmarksLoading
import com.trd.freenewsapp.states.NewsState
import com.trd.freenewsapp.states.NewsState.NewsLoading
import com.trd.freenewsapp.usecases.AddBookmarkUseCase
import com.trd.freenewsapp.usecases.LoadBookmarksUseCase
import com.trd.freenewsapp.usecases.RemoveBookmarkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarksViewModel @Inject constructor(
    private val loadBookmarksUseCase: LoadBookmarksUseCase,
    private val addBookmarkUseCase: AddBookmarkUseCase,
    private val removeBookmarkUseCase: RemoveBookmarkUseCase,
) : ViewModel() {

    private val _bookmarksLiveData = MutableLiveData<BookmarksState>()
    val bookmarksLiveData: LiveData<BookmarksState> get() = _bookmarksLiveData

    fun loadBookmarks() {
        _bookmarksLiveData.postValue(BookmarksLoading)
        viewModelScope.launch(Dispatchers.IO) {
            val result = loadBookmarksUseCase.loadBookmarks()
            Log.i(LOG_TAG, "loadBookmarks:BookmarksViewModel result = $result")
            _bookmarksLiveData.postValue(result)
        }
    }

    fun loadBookmarksByQuery(query: String) {
        _bookmarksLiveData.postValue(BookmarksLoading)
        viewModelScope.launch(Dispatchers.IO) {
            val result = loadBookmarksUseCase.loadBookmarksByQuery(query)
            Log.i(LOG_TAG, "loadBookmarksByQuery:BookmarksViewModel result = $result")
            _bookmarksLiveData.postValue(result)
        }
    }


    fun addBookmark(newsItem: NewsItem) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = addBookmarkUseCase.addBookmark(newsItem)
            Log.i(LOG_TAG, "addBookmark:BookmarksViewModel result = $result")
            _bookmarksLiveData.postValue(result)
        }

    }

    fun removeBookmark(newsItem: NewsItem) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = removeBookmarkUseCase.removeBookmark(newsItem)
            Log.i(LOG_TAG, "addBookmark:BookmarksViewModel result = $result")
            _bookmarksLiveData.postValue(result)
        }
    }

}