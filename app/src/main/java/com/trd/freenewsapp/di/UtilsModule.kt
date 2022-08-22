package com.trd.freenewsapp.di

import android.content.Context
import com.trd.freenewsapp.utils.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UtilsModule {

    @Singleton
    @Provides
    fun provideToastUtils(@ApplicationContext context: Context): ToastUtils =
        ToastUtilsImpl(context)

    @Provides
    fun provideImageLoader(@ApplicationContext context: Context): ImageLoader =
        ImageLoaderImpl(context)

    @Provides
    fun provideWebViewLoader(): WebViewLoader = WebViewLoaderImpl()

}
