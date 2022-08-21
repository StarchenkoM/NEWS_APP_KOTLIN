package com.trd.freenewsapp.usecases

import com.trd.freenewsapp.homescreen.NewsState
import com.trd.freenewsapp.repository.HomeScreenRepository

class LoadNewsUseCaseImpl(private val repository: HomeScreenRepository) : LoadNewsUseCase {
    override suspend fun loadNews(): NewsState {
        return repository.loadNews()
    }
}