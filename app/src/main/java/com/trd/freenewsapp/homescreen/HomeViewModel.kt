package com.trd.freenewsapp.homescreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trd.freenewsapp.constants.Constants.LOG_TAG
import com.trd.freenewsapp.homescreen.NewsState.NewsLoading
import com.trd.freenewsapp.usecases.LoadNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val loadNewsUseCase: LoadNewsUseCase,
) : ViewModel() {

    private val _newsLiveData = MutableLiveData<NewsState>()
    val newsLiveData: LiveData<NewsState> get() = _newsLiveData

    fun loadNews() {
        _newsLiveData.postValue(NewsLoading)
        viewModelScope.launch(Dispatchers.IO) {
            val result = loadNewsUseCase.loadNews()
            Log.i(LOG_TAG, "loadNews:HomeViewModel result = $result")
            _newsLiveData.postValue(result)
        }
    }

}