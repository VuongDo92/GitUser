package com.tymex.gituser.domain

data class GitUserData(
    val id: Long,
    val login: String,
    val avatarUrl: String?,
    val htmlUrl: String?,
    val location: String?,
    val name: String?,
    val following: Long?,
    val followers: Long?
)
