package com.trd.freenewsapp.homescreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trd.freenewsapp.homescreen.adapters.NewsItem
import com.trd.freenewsapp.states.BookmarksState
import com.trd.freenewsapp.states.NewsState
import com.trd.freenewsapp.states.NewsState.NewsLoading
import com.trd.freenewsapp.usecases.AddBookmarkUseCase
import com.trd.freenewsapp.usecases.LoadNewsUseCase
import com.trd.freenewsapp.utils.NetworkHelper
import com.trd.freenewsapp.utils.ToastUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val loadNewsUseCase: LoadNewsUseCase,
    private val addBookmarkUseCase: AddBookmarkUseCase,
    private val networkHelper: NetworkHelper,
    private val toastUtils: ToastUtils,
) : ViewModel() {

    private val _newsLiveData = MutableLiveData<NewsState>()
    val newsLiveData: LiveData<NewsState> get() = _newsLiveData

    private val _bookmarksLiveData = MutableLiveData<BookmarksState>()
    val bookmarksLiveData: LiveData<BookmarksState> get() = _bookmarksLiveData

    private var currentPage = 1

    fun loadNews() {
        if (networkHelper.isOnline()) {
            _newsLiveData.postValue(NewsLoading)
            viewModelScope.launch(Dispatchers.IO) {
                val result = loadNewsUseCase.loadNews()
                _newsLiveData.postValue(result)
            }
        } else {
            toastUtils.showNetworkErrorToast()
        }
    }

    fun loadNewsByQuery(query: String) {
        if (networkHelper.isOnline()) {
            _newsLiveData.postValue(NewsLoading)
            viewModelScope.launch(Dispatchers.IO) {
                val result = loadNewsUseCase.loadNewsByQuery(query)
                _newsLiveData.postValue(result)
            }
        } else {
            toastUtils.showNetworkErrorToast()
        }
    }

    fun addBookmark(newsItem: NewsItem) {
        if (networkHelper.isOnline()) {
            viewModelScope.launch(Dispatchers.IO) {
                val result = addBookmarkUseCase.addBookmark(newsItem)
                _bookmarksLiveData.postValue(result)
            }
        } else {
            toastUtils.showNetworkErrorToast()
        }
    }

    private fun getCurrentPage(): Int = if (currentPage == 1) ++currentPage else currentPage

    fun increasePageNumber() {
        ++currentPage
    }

    fun loadMoreNews() {
        if (networkHelper.isOnline()) {
            val currentPage = getCurrentPage()
            _newsLiveData.postValue(NewsLoading)
            viewModelScope.launch(Dispatchers.IO) {
                val result = loadNewsUseCase.loadNewsByPage(currentPage)
                _newsLiveData.postValue(result)
            }
        } else {
            toastUtils.showNetworkErrorToast()
        }
    }

}