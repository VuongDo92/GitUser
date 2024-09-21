package com.tymex.gituser.data.di

import com.tymex.gituser.data.GitUserRepositoryImpl
import com.tymex.gituser.domain.GitUserRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module


val gitUserDataModule = module {
    singleOf(::GitUserRepositoryImpl).bind<GitUserRepository>()
}