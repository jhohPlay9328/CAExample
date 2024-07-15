package com.jh.oh.play.domain.entity

sealed class UIState {
    companion object{
        const val LOADING_NONE = -1
        const val LOADING_GONE = 0
        const val LOADING_SHOW_DEFAULT = 1
    }
    data class Loading(val loadingType: Int = LOADING_SHOW_DEFAULT): UIState()
    data class ResultSuccess<T>(val callbackId: Enum<*>, val data: T): UIState()
    data class ResultFail<T>(val callbackId: Enum<*>,val data: T): UIState()
    data class NetworkFail(val callbackId: Enum<*>, val errorMessage: String): UIState()
}