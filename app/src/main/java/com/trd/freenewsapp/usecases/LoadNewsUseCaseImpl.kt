package com.trd.freenewsapp.usecases

import com.trd.freenewsapp.states.NewsState
import com.trd.freenewsapp.repository.HomeScreenRepository

class LoadNewsUseCaseImpl(private val repository: HomeScreenRepository) : LoadNewsUseCase {
    override suspend fun loadNews(): NewsState {
        return repository.loadNews()
    }

    override suspend fun loadNewsByQuery(query: String): NewsState {
        return repository.loadNewsByQuery(query)
    }

    override suspend fun loadNewsByPage(page: Int): NewsState {
        return repository.loadNewsByPage(page)
    }
}