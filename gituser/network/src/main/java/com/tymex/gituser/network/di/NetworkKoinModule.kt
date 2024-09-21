package com.tymex.gituser.network.di

import com.tymex.core.domain.git_user.RemoteGitUserDataSource
import com.tymex.gituser.network.KtorRemoteGitUserDataSource
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val networkModule = module {
    singleOf(::KtorRemoteGitUserDataSource).bind<RemoteGitUserDataSource>()
}