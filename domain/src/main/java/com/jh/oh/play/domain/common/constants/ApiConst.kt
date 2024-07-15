package com.jh.oh.play.domain.common.constants

object ApiConst {
    object Param {
        const val API_NAME = "apiName"
    }

    object ApiName {
        // 네이버 로그인 유저 정보
        const val NAVER_ME = "nid/me"
    }

    object ApiCode {
        // 네이버 로그인 유저 정보
        const val NAVER_ME = "naver"
    }

    enum class CallbackId(val apiName: String, val apiCode: String) {
        NAVER_USER_DATA(
            ApiName.NAVER_ME,
            ApiCode.NAVER_ME
        ),

        TEXT_HELPER_INSERT("", ""),
        TEXT_HELPER_LOAD_ALL_LIST("", ""),
        TEXT_HELPER_DELETE_BY_ID("", ""),
    }
}