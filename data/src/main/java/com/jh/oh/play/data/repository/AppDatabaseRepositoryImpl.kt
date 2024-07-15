package com.jh.oh.play.data.repository

import com.jh.oh.play.data.common.util.RetrofitUtil
import com.jh.oh.play.data.datasource.AppDatabaseRemote
import com.jh.oh.play.data.mapper.mapperTextHelper
import com.jh.oh.play.data.mapper.mapperTextHelperList
import com.jh.oh.play.data.model.database.TextHelperRes
import com.jh.oh.play.domain.entity.UIState
import com.jh.oh.play.domain.model.database.TextHelperModel
import com.jh.oh.play.domain.repository.AppDatabaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class AppDatabaseRepositoryImpl @Inject constructor(
    private val appDatabaseRemote: AppDatabaseRemote
): AppDatabaseRepository {
    override suspend fun insertTextHelper(callbackId: Enum<*>, textHelperData: String) = flow {
        appDatabaseRemote.insertTextHelper(textHelperData).apply {
            emit(UIState.ResultSuccess(callbackId, this))
        }
    }
        .flowOn(Dispatchers.IO)
        .catch {}

    override suspend fun updateTextHelper(callbackId: Enum<*>, textHelperData: String) = flow {
        appDatabaseRemote.updateTextHelper(textHelperData).apply {
            emit(UIState.ResultSuccess(callbackId, this))
        }
    }
        .flowOn(Dispatchers.IO)
        .catch {}

    override suspend fun deleteTextHelperById(callbackId: Enum<*>, id: Int) = flow {
        appDatabaseRemote.deleteTextHelperById(id).apply {
            emit(UIState.ResultSuccess(callbackId, this))
        }
    }
        .flowOn(Dispatchers.IO)
        .catch {}

    override suspend fun deleteTextHelperAllList(callbackId: Enum<*>) = flow {
        appDatabaseRemote.deleteTextHelperAllList().apply {
            emit(UIState.ResultSuccess(callbackId, this))
        }
    }
        .flowOn(Dispatchers.IO)
        .catch {}

    override suspend fun loadTextHelperById(callbackId: Enum<*>, id: Int) = flow {
        appDatabaseRemote.loadTextHelperById(id).apply {
            RetrofitUtil.responseRoomEmit<TextHelperRes, TextHelperModel>(
                callbackId,
                this,
                ::mapperTextHelper
            ).apply {
                emit(this)
            }
        }
    }
        .flowOn(Dispatchers.IO)
        .catch {}

    override suspend fun loadTextHelperAllList(callbackId: Enum<*>) = flow {
        appDatabaseRemote.loadTextHelperAllList().apply {
            RetrofitUtil.responseRoomEmit<List<TextHelperRes>, List<TextHelperModel>>(
                callbackId,
                this,
                ::mapperTextHelperList
            ).apply {
                emit(this)
            }
        }
    }
        .flowOn(Dispatchers.IO)
        .catch {}
}