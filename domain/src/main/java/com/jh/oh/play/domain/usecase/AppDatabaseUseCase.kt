package com.jh.oh.play.domain.usecase

import com.jh.oh.play.domain.repository.AppDatabaseRepository
import javax.inject.Inject

class AppDatabaseUseCase @Inject constructor(
    private val appDatabaseRepository: AppDatabaseRepository
){
    suspend fun insertTextHelper(callbackId: Enum<*>, textHelperData: String) =
        appDatabaseRepository.insertTextHelper(callbackId, textHelperData)

    suspend fun updateTextHelper(callbackId: Enum<*>, textHelperData: String) =
        appDatabaseRepository.updateTextHelper(callbackId, textHelperData)

    suspend fun deleteTextHelperById(callbackId: Enum<*>, id: Int) =
        appDatabaseRepository.deleteTextHelperById(callbackId, id)

    suspend fun deleteTextHelperAllList(callbackId: Enum<*>) {
        appDatabaseRepository.deleteTextHelperAllList(callbackId)
    }

    suspend fun loadTextHelperById(callbackId: Enum<*>, id: Int) =
        appDatabaseRepository.loadTextHelperById(callbackId, id)

    suspend fun loadTextHelperAllList(callbackId: Enum<*>) = appDatabaseRepository.loadTextHelperAllList(callbackId)
}