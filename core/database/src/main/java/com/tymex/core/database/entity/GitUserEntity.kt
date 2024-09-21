package com.tymex.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.bson.types.ObjectId

@Entity
data class GitUserEntity(
    val id: Long,
    val login: String,
    val avatarUrl: String?,
    val htmlUrl: String?,
    val location: String?,
    val name: String?,
    val blog: String?,
    val following: Long?,
    val followers: Long?,
    @PrimaryKey(autoGenerate = false)
    val localId: String = ObjectId().toHexString()
)
