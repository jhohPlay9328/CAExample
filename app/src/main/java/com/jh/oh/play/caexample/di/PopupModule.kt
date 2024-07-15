package com.jh.oh.play.caexample.di

import com.jh.oh.play.caexample.ui.dialog.DialogManager
import com.jh.oh.play.caexample.ui.dialogfragment.DialogFragmentManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class PopupModule {
    @Singleton
    @Provides
    fun provideDialogManager() = DialogManager()

    @Singleton
    @Provides
    fun provideDialogFragmentManager() = DialogFragmentManager()
}