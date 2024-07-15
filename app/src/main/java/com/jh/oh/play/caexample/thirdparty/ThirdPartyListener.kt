package com.jh.oh.play.caexample.thirdparty

abstract class ThirdPartyListener {
    open fun onAuthSuccessNaver(accessToken: String) {}
    open fun onAuthFail(errorMessage: String) {}
}