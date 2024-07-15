package com.jh.oh.play.data.di

import com.jh.oh.play.data.service.NaverService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    companion object {
        const val USER_AGENT_VALUE = "PopCaster_2.0_AOS"

        // Retrofit 라이브러리를 사용하면 url 마지막에 "/"를 적어야 정상 동작함.

        private const val NAVER_API_URL = "https://openapi.naver.com/v1/"

        private const val TIMEOUT_CONNECT = 3000L
        private const val TIMEOUT_READ = 3000L
        private const val TIMEOUT_WRITE = 3000L
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            connectTimeout(TIMEOUT_CONNECT, TimeUnit.MILLISECONDS)
            writeTimeout(TIMEOUT_READ, TimeUnit.MILLISECONDS)
            readTimeout(TIMEOUT_WRITE, TimeUnit.MILLISECONDS)
            retryOnConnectionFailure(true)
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }.build()
    }

    // Singleton으로 생성한 인스턴스는 변경도 되지 않고 동일한 인스턴스를 생성할 수 없다.
    // Naver API는 url이 달라서 해당 retrofit 인스터스를 사용한다.
    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(NAVER_API_URL)
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit): NaverService {
        return retrofit.create(NaverService::class.java)
    }
}