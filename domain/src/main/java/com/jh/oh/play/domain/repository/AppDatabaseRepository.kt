package com.jh.oh.play.domain.repository

import com.jh.oh.play.domain.entity.UIState
import kotlinx.coroutines.flow.Flow

interface AppDatabaseRepository {
    suspend fun insertTextHelper(callbackId: Enum<*>, textHelperData: String): Flow<UIState>
    suspend fun updateTextHelper(callbackId: Enum<*>, textHelperData: String): Flow<UIState>
    suspend fun deleteTextHelperById(callbackId: Enum<*>, id: Int): Flow<UIState>
    suspend fun deleteTextHelperAllList(callbackId: Enum<*>): Flow<UIState>
    suspend fun loadTextHelperById(callbackId: Enum<*>, id: Int): Flow<UIState>
    suspend fun loadTextHelperAllList(callbackId: Enum<*>): Flow<UIState>
}