package com.jh.oh.play.data.di

import com.jh.oh.play.data.repository.AppDatabaseRepositoryImpl
import com.jh.oh.play.data.repository.NaverRepositoryImpl
import com.jh.oh.play.domain.repository.AppDatabaseRepository
import com.jh.oh.play.domain.repository.NaverRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Singleton
    @Provides
    fun provideAppDatabaseRepository(appDatabaseRepositoryImpl: AppDatabaseRepositoryImpl):
            AppDatabaseRepository = appDatabaseRepositoryImpl

    @Singleton
    @Provides
    fun provideNaverRepository(naverRepositoryImpl: NaverRepositoryImpl):
            NaverRepository = naverRepositoryImpl
}