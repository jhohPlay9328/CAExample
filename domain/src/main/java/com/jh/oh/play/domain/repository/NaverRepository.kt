package com.jh.oh.play.domain.repository

import com.jh.oh.play.domain.entity.UIState
import kotlinx.coroutines.flow.Flow

interface NaverRepository {
    suspend fun requestNaverUserData(
        callbackId: Enum<*>,
        clientId: String,
        clientSecret: String,
        authorization: String
    ) : Flow<UIState>
}