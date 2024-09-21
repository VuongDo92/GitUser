package com.tymex.gituser.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GitUserDto(
    @SerialName("id") val id: Long,
    @SerialName("login") val login: String,
    @SerialName("avatar_url") val avatarUrl: String? = null,
    @SerialName("html_url") val htmlUrl: String? = null,
    @SerialName("location") val location: String? = null,
    @SerialName("blog") val blog: String? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("following") val following: Long? = 0L,
    @SerialName("followers") val followers: Long? = 0L
)
