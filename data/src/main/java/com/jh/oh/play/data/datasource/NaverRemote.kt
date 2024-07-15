package com.jh.oh.play.data.datasource

import com.jh.oh.play.data.model.NaverUserRes
import com.jh.oh.play.data.service.NaverService
import retrofit2.Response
import javax.inject.Inject

class NaverRemote @Inject constructor(
    private val naverService: NaverService
){
    suspend fun requestNaverUserData(
        clientId: String,
        clientSecret: String,
        authorization: String
    ): Response<NaverUserRes> {
        return naverService.requestNaverUserData(clientId, clientSecret, authorization)
    }
}