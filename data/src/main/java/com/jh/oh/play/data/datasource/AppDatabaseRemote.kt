package com.jh.oh.play.data.datasource

import com.jh.oh.play.data.common.util.RetrofitUtil
import com.jh.oh.play.data.dao.TextHelperDAO
import javax.inject.Inject

class AppDatabaseRemote @Inject constructor(
    private val textHelperDAO: TextHelperDAO
){
    suspend fun insertTextHelper(textPreset: String) = textHelperDAO.insertTextHelper(
        RetrofitUtil.createStringToTextPresetModel(textPreset)
    )
    suspend fun updateTextHelper(textPreset: String) = textHelperDAO.updateTextHelper(
        RetrofitUtil.createStringToTextPresetModel(textPreset)
    )
    suspend fun deleteTextHelperById(id: Int) = textHelperDAO.deleteTextHelperById(id)
    suspend fun deleteTextHelperAllList() = textHelperDAO.deleteTextHelperAllList()
    suspend fun loadTextHelperById(id: Int) = textHelperDAO.loadTextHelperById(id)
    suspend fun loadTextHelperAllList() = textHelperDAO.loadTextHelperAllList()
}