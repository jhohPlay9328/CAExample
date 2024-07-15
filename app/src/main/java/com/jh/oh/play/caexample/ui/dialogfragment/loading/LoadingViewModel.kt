package com.jh.oh.play.caexample.ui.dialogfragment.loading

import android.app.Application
import com.jh.oh.play.caexample.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoadingViewModel@Inject constructor(
    application: Application
): BaseViewModel(application){
    override suspend fun <T>resultUseCaseSuccess(callbackId: Enum<*>, data: T){}

    override suspend fun <T>resultUseCaseFail(callbackId: Enum<*>, data: T){}

    override suspend fun resultUseCaseNetworkFail(callbackId: Enum<*>, errorMessage: String) {}
}