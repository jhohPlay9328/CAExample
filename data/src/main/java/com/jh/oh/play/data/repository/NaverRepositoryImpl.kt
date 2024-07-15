package com.jh.oh.play.data.repository

import com.jh.oh.play.data.common.util.RetrofitUtil
import com.jh.oh.play.data.datasource.NaverRemote
import com.jh.oh.play.data.mapper.mapperNaverUser
import com.jh.oh.play.data.model.NaverUserRes
import com.jh.oh.play.domain.model.database.NaverUserResModel
import com.jh.oh.play.domain.repository.NaverRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.retryWhen
import javax.inject.Inject


class NaverRepositoryImpl @Inject constructor(
    private val naverRemote: NaverRemote,
): NaverRepository {
    override suspend fun requestNaverUserData(
        callbackId: Enum<*>,
        clientId: String,
        clientSecret: String,
        authorization: String
    ) = flow {
        naverRemote.requestNaverUserData(
            clientId,
            clientSecret,
            authorization
        ).apply {
            RetrofitUtil.responseEmit<NaverUserRes, NaverUserResModel>(
                callbackId,
                this,
                ::mapperNaverUser
            ).apply {
                emit(this)
            }
        }
    }
        .flowOn(Dispatchers.IO)
        .retryWhen { cause, attempt ->
            RetrofitUtil.retryRetrofit(callbackId, cause, attempt, this)
        }
        .catch {}
}