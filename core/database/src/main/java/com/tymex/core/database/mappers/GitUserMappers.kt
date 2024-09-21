package com.tymex.core.database.mappers

import com.tymex.core.database.entity.GitUserEntity
import com.tymex.core.domain.git_user.GitUser

fun GitUserEntity.toGitUser(): GitUser {
    return GitUser(
        id = id,
        login = login,
        avatarUrl = avatarUrl,
        htmlUrl = htmlUrl,
        location = location,
        name = name,
        blog = blog,
        following = following,
        followers = followers,
        localId = localId
    )
}

fun GitUser.toGitUserEntity(): GitUserEntity {
    return GitUserEntity(
        id = id,
        login = login,
        avatarUrl = avatarUrl,
        htmlUrl = htmlUrl,
        name = name,
        location = location,
        blog = blog,
        following = following,
        followers = followers,
        localId = localId ?: ""
    )
}