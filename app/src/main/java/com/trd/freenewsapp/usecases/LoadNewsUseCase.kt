package com.trd.freenewsapp.usecases

import com.trd.freenewsapp.homescreen.NewsState

interface LoadNewsUseCase {
    suspend fun loadNews(): NewsState
}