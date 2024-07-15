package com.jh.oh.play.domain.di

import com.jh.oh.play.domain.repository.AppDatabaseRepository
import com.jh.oh.play.domain.repository.NaverRepository
import com.jh.oh.play.domain.usecase.AppDatabaseUseCase
import com.jh.oh.play.domain.usecase.NaverUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
    @Singleton
    @Provides
    fun provideNaverUseCase(naverRepository: NaverRepository) = NaverUseCase(naverRepository)

    @Singleton
    @Provides
    fun provideAppDatabaseRepository(appDatabaseRepository: AppDatabaseRepository) = AppDatabaseUseCase(appDatabaseRepository)
}