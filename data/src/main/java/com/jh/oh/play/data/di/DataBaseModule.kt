package com.jh.oh.play.data.di

import android.app.Application
import androidx.room.Room
import com.jh.oh.play.data.common.constants.DatabaseConst
import com.jh.oh.play.data.dao.TextHelperDAO
import com.jh.oh.play.data.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(application: Application): AppDatabase {

        return Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            DatabaseConst.Param.MAIN_RENAME
        ).allowMainThreadQueries().build()
    }

    @Provides
    @Singleton
    fun provideTextHelperDAO(appDatabase: AppDatabase): TextHelperDAO {
        return appDatabase.textHelperDAO()
    }
}