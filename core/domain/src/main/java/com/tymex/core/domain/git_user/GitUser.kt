package com.tymex.core.domain.git_user

data class GitUser(
    val id: Long,
    val login: String,
    val avatarUrl: String? = null,
    val htmlUrl: String? = null,
    val location: String? = null,
    val name: String? = null,
    val blog: String? = null,
    val following: Long? = null,
    val followers: Long? = null,
    val localId: String? = null
)
