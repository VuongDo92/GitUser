package com.tymex.demo

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.tymex.core.domain.git_user.GitUser
import com.tymex.gituser.presentation.git_user_detail.GitUserDetailScreenRoot
import com.tymex.gituser.presentation.git_user_list.GitUserListScreenRoot


@Composable
fun NavigationRoot(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = "main"
    ) {
        mainGraph(navController)
    }
}

private fun NavGraphBuilder.mainGraph(navController: NavHostController) {
    navigation(
        startDestination = "git_user_list",
        route = "main"
    ) {
        composable(
            route = "git_user_list"
        ) {
            GitUserListScreenRoot(
                onGitUserItemClick = { gitUser: GitUser ->
                    navController.navigate(
                        route = "git_user_detail/${gitUser.login}") {
                        popUpTo("git_user_list") {
                            inclusive = false
                            saveState = true
                        }
                        restoreState = true
                    }
                }
            )
        }
        composable(
            route = "git_user_detail/{git_user_login}",
            arguments = listOf(
                navArgument(name = "git_user_login") {
                    type = NavType.StringType
                }
            )
        ) {
            val gitUserLogin = it.arguments?.getString("git_user_login") ?: ""
                GitUserDetailScreenRoot(
                    gitUserLogin = gitUserLogin,
                    onBack = {
                        navController.popBackStack(
                            route = "git_user_list",
                            inclusive = false,
                        )
                    }
                )
        }
    }
}