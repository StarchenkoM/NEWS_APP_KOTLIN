package com.trd.freenewsapp.homescreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trd.freenewsapp.constants.Constants.LOG_TAG
import com.trd.freenewsapp.homescreen.adapters.NewsItem
import com.trd.freenewsapp.states.BookmarksState
import com.trd.freenewsapp.states.NewsState
import com.trd.freenewsapp.states.NewsState.NewsLoading
import com.trd.freenewsapp.usecases.AddBookmarkUseCase
import com.trd.freenewsapp.usecases.LoadNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val loadNewsUseCase: LoadNewsUseCase,
    private val addBookmarkUseCase: AddBookmarkUseCase,
) : ViewModel() {

    private val _newsLiveData = MutableLiveData<NewsState>()
    val newsLiveData: LiveData<NewsState> get() = _newsLiveData

//TODO check it
    private val _bookmarksLiveData = MutableLiveData<BookmarksState>()
    val bookmarksLiveData: LiveData<BookmarksState> get() = _bookmarksLiveData

    fun loadNews() {
        _newsLiveData.postValue(NewsLoading)
        viewModelScope.launch(Dispatchers.IO) {
            val result = loadNewsUseCase.loadNews()
            Log.i(LOG_TAG, "loadNews:HomeViewModel result = $result")
            _newsLiveData.postValue(result)
        }
    }

    fun loadNewsByQuery(query: String) {
        _newsLiveData.postValue(NewsLoading)
        viewModelScope.launch(Dispatchers.IO) {
            val result = loadNewsUseCase.loadNewsByQuery(query)
            Log.i(LOG_TAG, "loadNews:HomeViewModel result = $result")
            _newsLiveData.postValue(result)
        }
    }

    fun addBookmark(newsItem: NewsItem) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = addBookmarkUseCase.addBookmark(newsItem)
            Log.i(LOG_TAG, "loadNews:BookmarksViewModel result = $result")
            _bookmarksLiveData.postValue(result)
        }
    }

}