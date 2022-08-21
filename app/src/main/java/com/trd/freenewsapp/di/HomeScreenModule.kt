package com.trd.freenewsapp.di

import com.trd.freenewsapp.database.NewsDao
import com.trd.freenewsapp.database.NewsMapper
import com.trd.freenewsapp.repository.HomeScreenRepository
import com.trd.freenewsapp.repository.HomeScreenRepositoryImpl
import com.trd.freenewsapp.usecases.LoadNewsUseCase
import com.trd.freenewsapp.usecases.LoadNewsUseCaseImpl
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
class AppModule {


    @IoCoroutineScope
    @Provides
    fun providesIoDispatcher(): CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)


    //Provide Repositories

    @Provides
    fun provideHomeScreenRepository(
        newsMapper: NewsMapper,
        newsDao: NewsDao,
    ): HomeScreenRepository =
        HomeScreenRepositoryImpl(newsMapper, newsDao)

/*    @Provides
    fun provideOpportunityRepository(
            roverMapper: RoverMapper,
            opportunityDao: OpportunityDao,
    ): OpportunityRepository =
            OpportunityRepositoryImpl(roverMapper, opportunityDao)*/

    //Rovers Use Cases
    @Provides
    fun provideLoadNewsUseCase(repository: HomeScreenRepository): LoadNewsUseCase =
        LoadNewsUseCaseImpl(repository)


}


@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class IoCoroutineScope
