package com.jh.oh.play.caexample

import android.app.Application
import com.jh.oh.play.domain.common.constants.ApiConst
import com.jh.oh.play.domain.model.database.NaverUserResModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    application: Application,
): BaseViewModel(application){
    private val _getNaverUserData = MutableSharedFlow<NaverUserResModel>()
    val getNaverUserData get() = _getNaverUserData

    fun requestNaverUserData(
        clientId: String,
        clientSecret: String,
        authorization: String
    ){
        super.requestNaverUserData(
            ApiConst.CallbackId.NAVER_USER_DATA,
            clientId,
            clientSecret,
            authorization
        )
    }

    override suspend fun <T>resultUseCaseSuccess(callbackId: Enum<*>, data: T){
        when(callbackId) {
            ApiConst.CallbackId.NAVER_USER_DATA -> {
                _getNaverUserData.emit(data as NaverUserResModel)
            }
        }
    }

    override suspend fun <T>resultUseCaseFail(callbackId: Enum<*>, data: T){
        when(callbackId) {
            ApiConst.CallbackId.NAVER_USER_DATA ->
                showApiErrorPopup((data as NaverUserResModel).message)
        }
    }

    override suspend fun resultUseCaseNetworkFail(callbackId: Enum<*>, errorMessage: String) {
        showApiErrorPopup(errorMessage)
    }
}