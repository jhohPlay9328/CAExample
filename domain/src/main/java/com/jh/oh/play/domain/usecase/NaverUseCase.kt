package com.jh.oh.play.domain.usecase

import com.jh.oh.play.domain.repository.NaverRepository
import javax.inject.Inject

class NaverUseCase @Inject constructor(
    private val naverRepository: NaverRepository
){
    suspend fun requestNaverUserData(
        callbackId: Enum<*>,
        clientId: String,
        clientSecret: String,
        authorization: String
    ) =
        naverRepository.requestNaverUserData(callbackId, clientId, clientSecret, authorization)
}