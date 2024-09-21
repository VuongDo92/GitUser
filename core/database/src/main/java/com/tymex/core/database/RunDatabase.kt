package com.tymex.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tymex.core.database.dao.GitUserDao
import com.tymex.core.database.entity.GitUserEntity

@Database(
    entities = [
        GitUserEntity::class
    ],
    version = 1
)
abstract class GitUserDatabase : RoomDatabase() {

    abstract val gitUserDao: GitUserDao
}