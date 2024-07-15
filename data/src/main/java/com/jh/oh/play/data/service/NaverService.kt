package com.jh.oh.play.data.service

import com.jh.oh.play.data.model.NaverUserRes
import com.jh.oh.play.domain.common.constants.ApiConst
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface NaverService {
    // 네이버 로그인 유저 정보
    @GET(ApiConst.ApiName.NAVER_ME)
    suspend fun requestNaverUserData(
        @Header("X-Naver-Client-Id") clientId: String,
        @Header("X-Naver-Client-Secret") clientSecret: String,
        @Header("Authorization") authorization: String
    ): Response<NaverUserRes>
}