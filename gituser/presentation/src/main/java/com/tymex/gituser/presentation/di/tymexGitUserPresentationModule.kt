package com.tymex.gituser.presentation.di

import com.tymex.gituser.presentation.git_user_detail.GitUserDetailViewModel
import com.tymex.gituser.presentation.git_user_list.GitUserListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val tymexGitUserPresentationModule = module {
    viewModelOf(::GitUserListViewModel)
    viewModel { (gitUserLogin: String) ->
        GitUserDetailViewModel(gitUserLogin, get())
    }
}