package com.jh.oh.play.caexample

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.jh.oh.play.domain.common.constants.ApiConst
import com.jh.oh.play.domain.entity.UIState
import com.jh.oh.play.domain.model.database.TextHelperModel
import com.jh.oh.play.domain.usecase.AppDatabaseUseCase
import com.jh.oh.play.domain.usecase.NaverUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@Suppress("SameParameterValue")
abstract class BaseViewModel(
    application: Application,
): AndroidViewModel(application) {
    protected val context get() = getApplication<Application>()

    @Inject internal lateinit var naverUseCase: NaverUseCase
    @Inject internal lateinit var appDatabaseUseCase: AppDatabaseUseCase

    private val _loadingDialogStatus = MutableSharedFlow<UIState.Loading>()
    val loadingDialogStatus = _loadingDialogStatus.asSharedFlow()

    private val _showApiErrorPopup = MutableSharedFlow<String>()
    val showApiErrorPopup get() = _showApiErrorPopup

    private val _showApiErrorFinishPopup = MutableSharedFlow<String>()
    val showApiErrorFinishPopup get() = _showApiErrorFinishPopup

    fun setLoadingDialogStatus(uiState: UIState.Loading) {
        viewModelScope.launch {
            _loadingDialogStatus.emit(uiState)
        }
    }
    fun showApiErrorPopup(errorMessage: String) {
        viewModelScope.launch {
            _showApiErrorPopup.emit(errorMessage)
        }
    }
    fun showApiErrorFinishPopup(errorMessage: String) {
        viewModelScope.launch {
            _showApiErrorFinishPopup.emit(errorMessage)
        }
    }

    private fun CoroutineScope.launchRequest(
        loadingType: Int = UIState.LOADING_SHOW_DEFAULT,
        block: suspend CoroutineScope.() -> Unit
    ): Job {
        setLoadingDialogStatus(UIState.Loading(loadingType))

        return launch {
            block()
        }
    }

    // 네이버 로그인 정보 가져오는 API
    protected open fun requestNaverUserData(
        callbackId: Enum<*>,
        clientId: String,
        clientSecret: String,
        authorization: String
    ){
        viewModelScope.launchRequest(UIState.LOADING_NONE) {
            naverUseCase.requestNaverUserData(
                callbackId,
                clientId,
                clientSecret,
                authorization
            ).collect {
                resultUseCase(it)
            }
        }
    }

    protected open fun insertTextHelper(
        callbackId: Enum<*>,
        textHelperModel: TextHelperModel,
    ) {
        viewModelScope.launchRequest{
            appDatabaseUseCase.insertTextHelper(
                callbackId,
                Gson().toJson(textHelperModel)
            ).collect {
                resultUseCase(it)
            }
        }
    }

    protected open fun updateTextHelper(
        callbackId: Enum<*>,
        textHelperModel: TextHelperModel,
    ) {
        viewModelScope.launchRequest{
            appDatabaseUseCase.updateTextHelper(
                callbackId,
                Gson().toJson(textHelperModel)
            ).collect {
                resultUseCase(it)
            }
        }
    }

    protected open fun deleteTextHelperById(
        callbackId: Enum<*>,
        id: Int,
    ) {
        viewModelScope.launchRequest{
            appDatabaseUseCase.deleteTextHelperById(
                callbackId,
                id
            ).collect {
                resultUseCase(it)
            }
        }
    }

    protected open fun deleteTextHelperAllList(
        callbackId: Enum<*>
    ) {
        viewModelScope.launchRequest{
            appDatabaseUseCase.deleteTextHelperAllList(callbackId)
        }
    }

    protected open fun loadTextHelperById(
        callbackId: Enum<*>,
        id: Int,
    ) {
        viewModelScope.launchRequest{
            appDatabaseUseCase.loadTextHelperById(
                callbackId,
                id
            ).collect {
                resultUseCase(it)
            }
        }
    }

    protected open fun loadTextHelperAllList(
        callbackId: Enum<*>
    ) {
        viewModelScope.launchRequest{
            appDatabaseUseCase.loadTextHelperAllList(
                callbackId
            ).collect {
                resultUseCase(it)
            }
        }
    }

    private suspend fun resultUseCase(uiState: UIState){
        when (uiState) {
            is UIState.Loading -> setLoadingDialogStatus(uiState)
            is UIState.ResultSuccess<*> -> {
                when(uiState.data) {
                    null -> {}
                    else -> {
                        resultUseCaseSuccess(uiState.callbackId, uiState.data)
                        setLoadingDialogStatus(UIState.Loading(UIState.LOADING_GONE))
                    }
                }
            }
            is UIState.ResultFail<*> -> {
                when(uiState.data) {
                    null -> {}
                    else -> {
                        resultUseCaseFail(uiState.callbackId, uiState.data)
                        setLoadingDialogStatus(UIState.Loading(UIState.LOADING_GONE))
                    }
                }
            }
            is UIState.NetworkFail -> {
                val apiName = (uiState.callbackId as ApiConst.CallbackId).apiName
                val apiCode = (uiState.callbackId as ApiConst.CallbackId).apiCode
                resultUseCaseNetworkFail(
                    uiState.callbackId,
                    context.getString(
                        R.string.error_network,
                        "$apiName($apiCode)"
                    )
                )
                setLoadingDialogStatus(UIState.Loading(UIState.LOADING_GONE))
            }
            else -> setLoadingDialogStatus(UIState.Loading(UIState.LOADING_GONE))
        }
    }

    // rstCode가 "0"인 경우
    abstract suspend fun <T>resultUseCaseSuccess(callbackId: Enum<*>, data: T)
    // rstCode가 "0"이 아닌 경우
    abstract suspend fun <T>resultUseCaseFail(callbackId: Enum<*>, data: T)
    // API 요청에 대한 응답 값을 받지 못한 경우( ex: timeout error, 500 error...)
    abstract suspend fun resultUseCaseNetworkFail(callbackId: Enum<*>, errorMessage: String)
}