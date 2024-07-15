package com.jh.oh.play.data.di

import com.jh.oh.play.data.dao.TextHelperDAO
import com.jh.oh.play.data.datasource.AppDatabaseRemote
import com.jh.oh.play.data.datasource.NaverRemote
import com.jh.oh.play.data.service.NaverService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {
    @Singleton
    @Provides
    fun provideNaverRemote(naverService: NaverService) = NaverRemote(naverService)

    @Singleton
    @Provides
    fun provideAppDatabaseRemote(
        textHelperDAO: TextHelperDAO
    ) = AppDatabaseRemote(textHelperDAO)

}