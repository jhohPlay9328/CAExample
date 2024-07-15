package com.jh.oh.play.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jh.oh.play.data.dao.TextHelperDAO
import com.jh.oh.play.data.model.database.TextHelperRes

@Database(
    entities = [
        TextHelperRes::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun textHelperDAO(): TextHelperDAO
}