package com.tymex.core.database.di

import androidx.room.Room
import com.tymex.core.database.GitUserDatabase
import com.tymex.core.database.RoomLocalGitUserDataSource
import com.tymex.core.domain.git_user.LocalGitUserDataSource
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            GitUserDatabase::class.java,
            "git_user.db"
        ).build()
    }
    single { get<GitUserDatabase>().gitUserDao }

    singleOf(::RoomLocalGitUserDataSource).bind<LocalGitUserDataSource>()
}