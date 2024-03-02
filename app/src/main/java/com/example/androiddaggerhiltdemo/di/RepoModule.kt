package com.example.androiddaggerhiltdemo.di

import android.app.Application
import com.example.androiddaggerhiltdemo.network.NewsRepository
import com.example.androiddaggerhiltdemo.network.RetrofitClient
import com.example.udhartask.network.NewsAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepoModule {
    @Provides
    @Singleton
    fun provideNewsRepo(application:Application,newsAPI:NewsAPI):NewsRepository{
        return NewsRepository(application,newsAPI)
    }

    @Provides
    @Singleton
    fun provideNewsApi():NewsAPI{
       return RetrofitClient.getInstance().create(NewsAPI::class.java)
    }
}