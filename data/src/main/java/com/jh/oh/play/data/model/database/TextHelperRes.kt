package com.jh.oh.play.data.model.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jh.oh.play.data.common.constants.DatabaseConst

@Entity(
    tableName = DatabaseConst.Param.TABLE_TEXT_HELPER,
)
data class TextHelperRes (
    @ColumnInfo(name = DatabaseConst.Param.COLUMN_POSITION_X)
    val positionX: Int,

    @ColumnInfo(name = DatabaseConst.Param.COLUMN_POSITION_Y)
    val positionY: Int,

    @ColumnInfo(name = DatabaseConst.Param.COLUMN_TEXT)
    val text: String,

    @ColumnInfo(name = DatabaseConst.Param.COLUMN_TEXT_SIZE)
    val textSize: Float,

    @ColumnInfo(name = DatabaseConst.Param.COLUMN_IS_BOLD)
    val isBold: Boolean,

    @ColumnInfo(name = DatabaseConst.Param.COLUMN_TEXT_COLOR)
    val textColor: Int,

    @ColumnInfo(name = DatabaseConst.Param.COLUMN_STROKE_SIZE)
    val strokeSize: Int,

    @ColumnInfo(name = DatabaseConst.Param.COLUMN_STROKE_COLOR)
    val strokeColor: Int,

    @ColumnInfo(name = DatabaseConst.Param.COLUMN_INSIDE_PADDING)
    val insidePadding: Int,

    @ColumnInfo(name = DatabaseConst.Param.COLUMN_BACKGROUND_COLOR)
    val backgroundColor: Int,
) {
    @ColumnInfo(name = DatabaseConst.Param.COLUMN_ID)
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}