package com.jh.oh.play.caexample.thirdparty

import android.content.Context
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.OAuthLoginCallback


object ThirdPartyManager {
    private var callback: ThirdPartyListener? = null
    //네이버 로그인*********************************************************************************
    private val naverCallback = object : OAuthLoginCallback {
        override fun onSuccess() {
            // 네이버 로그인 인증이 성공했을 때 수행할 코드 추가
            NaverIdLoginSDK.getAccessToken()?.let {
                callback?.onAuthSuccessNaver(it)
            }
        }
        override fun onFailure(httpStatus: Int, message: String) {
            NaverIdLoginSDK.getLastErrorDescription()?.let {
                callback?.onAuthFail(it)
            }
        }
        override fun onError(errorCode: Int, message: String) {
            onFailure(errorCode, message)
        }
    }

    fun loginNaver(context: Context, callback: ThirdPartyListener?){
        ThirdPartyManager.callback = callback
        NaverIdLoginSDK.authenticate(context, naverCallback)
    }
    //네이버 로그인*********************************************************************************

    fun logOutNaver() {
        NaverIdLoginSDK.logout()
    }

    fun logOutSNS(context: Context) {
        logOutNaver()
    }
}