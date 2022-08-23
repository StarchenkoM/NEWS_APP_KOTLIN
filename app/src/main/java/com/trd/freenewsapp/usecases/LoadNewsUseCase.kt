package com.trd.freenewsapp.usecases

import com.trd.freenewsapp.states.NewsState

interface LoadNewsUseCase {
    suspend fun loadNews(): NewsState
    suspend fun loadNewsByQuery(query: String): NewsState
    suspend fun loadNewsByPage(page: Int): NewsState
}