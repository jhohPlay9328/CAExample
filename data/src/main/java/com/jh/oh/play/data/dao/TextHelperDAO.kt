package com.jh.oh.play.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jh.oh.play.data.common.constants.DatabaseConst
import com.jh.oh.play.data.model.database.TextHelperRes

@Dao
interface TextHelperDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTextHelper(textHelperRes: TextHelperRes): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTextHelper(textHelperRes: TextHelperRes): Int

    @Query("DELETE FROM ${DatabaseConst.Param.TABLE_TEXT_HELPER} " +
            "WHERE ${DatabaseConst.Param.COLUMN_ID} = :id")
    suspend fun deleteTextHelperById(id: Int): Int

    @Query("DELETE FROM ${DatabaseConst.Param.TABLE_TEXT_HELPER}")
    suspend fun deleteTextHelperAllList()

    @Query("SELECT * " +
            "FROM ${DatabaseConst.Param.TABLE_TEXT_HELPER} " +
            "WHERE ${DatabaseConst.Param.COLUMN_ID} = :id")
    suspend fun loadTextHelperById(id: Int): TextHelperRes

    @Query("SELECT * " +
            "FROM ${DatabaseConst.Param.TABLE_TEXT_HELPER} ")
    suspend fun loadTextHelperAllList(): List<TextHelperRes>
}