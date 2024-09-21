package com.tymex.gituser.network

import com.tymex.core.domain.git_user.GitUser

fun GitUserDto.toGitUser(): GitUser {
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
        localId = null
    )
}