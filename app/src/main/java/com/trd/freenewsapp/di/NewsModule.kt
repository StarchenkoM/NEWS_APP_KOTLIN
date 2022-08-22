package com.trd.freenewsapp.di

import com.trd.freenewsapp.database.NewsDao
import com.trd.freenewsapp.database.NewsMapper
import com.trd.freenewsapp.repository.BookmarksRepository
import com.trd.freenewsapp.repository.BookmarksRepositoryImpl
import com.trd.freenewsapp.repository.HomeScreenRepository
import com.trd.freenewsapp.repository.HomeScreenRepositoryImpl
import com.trd.freenewsapp.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
class NewsModule {


    @IoCoroutineScope
    @Provides
    fun providesIoDispatcher(): CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)


    //Repositories
    @Provides
    fun provideHomeScreenRepository(
        newsMapper: NewsMapper,
        newsDao: NewsDao,
    ): HomeScreenRepository =
        HomeScreenRepositoryImpl(newsMapper, newsDao)

    @Provides
    fun provideBookmarksRepository(
        newsMapper: NewsMapper,
        newsDao: NewsDao,
        @IoCoroutineScope ioCoroutineScope: CoroutineScope,
    ): BookmarksRepository =
        BookmarksRepositoryImpl(newsMapper, newsDao, ioCoroutineScope)


    //Use Cases
    @Provides
    fun provideLoadNewsUseCase(repository: HomeScreenRepository): LoadNewsUseCase =
        LoadNewsUseCaseImpl(repository)

    @Provides
    fun provideLoadBookmarksUseCase(repository: BookmarksRepository): LoadBookmarksUseCase =
        LoadBookmarksUseCaseImpl(repository)

    @Provides
    fun provideAddBookmarkUseCase(repository: BookmarksRepository): AddBookmarkUseCase =
        AddBookmarkUseCaseImpl(repository)

    @Provides
    fun provideRemoveBookmarkUseCase(repository: BookmarksRepository): RemoveBookmarkUseCase =
        RemoveBookmarkUseCaseImpl(repository)

}

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class IoCoroutineScope
