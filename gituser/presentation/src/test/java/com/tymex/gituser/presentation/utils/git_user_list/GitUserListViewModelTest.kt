package com.tymex.gituser.presentation.utils.git_user_list

import com.tymex.test.MainCoroutineExtension
import com.tymex.core.domain.git_user.GitUser
import com.tymex.core.domain.util.Constants
import com.tymex.core.domain.util.Result
import com.tymex.gituser.domain.GitUserRepository
import com.tymex.gituser.presentation.git_user_list.GitUserListAction
import com.tymex.gituser.presentation.git_user_list.GitUserListViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

@ExperimentalCoroutinesApi
class GitUserListViewModelTest {

    companion object {
        @JvmField
        @RegisterExtension
        val mainCoroutineExtension = MainCoroutineExtension()
    }

    private lateinit var viewModel: GitUserListViewModel

    private lateinit var gitUserRepository: GitUserRepository

    private lateinit var testDispatcher: TestDispatcher

    private lateinit var testScope: CoroutineScope

    @BeforeEach
    fun setup() {
        testDispatcher = mainCoroutineExtension.testDispatcher
        testScope = CoroutineScope(testDispatcher)

        gitUserRepository = mockk()
        viewModel = GitUserListViewModel(gitUserRepository)
    }

    @Test
    fun `initial state is loading and fetches first page`() = runTest {
        // Given
        val mockUsers = listOf(GitUser(1L, "User1"), GitUser(2L, "User2"))

        // Mock the fetchGitUsers call to return the Result.Success with the mockUsers list
        coEvery { gitUserRepository.fetchGitUsers(any(), any()) } returns Result.Success(mockUsers)

        // When
        testScope.launch {
            val result = gitUserRepository.fetchGitUsers(currentPage = 0, pageSize = Constants.DEFAULT_PAGE_SIZE)

            // Then - Verify the result
            assert(result is Result.Success)
            assertEquals(mockUsers, (result as Result.Success).data)
        }
    }

    @Test
    fun `onAction OnRefresh fetches first page of users`() = runTest {
        // Given
        val mockUsers = listOf(GitUser(1L, "User1"), GitUser(2L, "User2"))
        coEvery { gitUserRepository.fetchGitUsers(currentPage = any(), pageSize = any()) } returns Result.Success(mockUsers)

        // When
        viewModel.onAction(GitUserListAction.OnRefresh)
        advanceUntilIdle() // Allow coroutine to complete

        // Then
        val updatedState = viewModel.state
        assertEquals(mockUsers, updatedState.dataList)
        assertEquals(false, updatedState.isLoading)
        assertEquals(false, updatedState.isRefreshing)
        assertEquals(false, updatedState.canLoadMore)
    }

    @Test
    fun `onAction OnLoadMore fetches next page of users`() = runTest {
        // Given
        val initialUsers = listOf(
            GitUser(1L, "User1"),
            GitUser(2L, "User2"),
            GitUser(1L, "User1"),
            GitUser(2L, "User2"),
            GitUser(1L, "User1"),
            GitUser(2L, "User2"),
            GitUser(1L, "User1"),
            GitUser(2L, "User2"),
            GitUser(1L, "User1"),
            GitUser(2L, "User2")
        )
        val moreUsers = listOf(GitUser(3L, "User3"))

        // Use any() to match any arguments
        coEvery { gitUserRepository.fetchGitUsers(any(), any()) } returnsMany listOf(
                Result.Success(initialUsers), // For the first call
                Result.Success(moreUsers) // For the second call
        )

        // Simulate initial load
        viewModel.onAction(GitUserListAction.OnRefresh)
        advanceUntilIdle() // Allow coroutine to complete initial refresh

        // Ensure `canLoadMore` is true after the first fetch
        assertEquals(true, viewModel.state.canLoadMore)

        // When
        viewModel.onAction(GitUserListAction.OnLoadMore)
        advanceUntilIdle() // Allow coroutine to complete loading more users

//        // Then
        val updatedState = viewModel.state
        assertEquals(initialUsers + moreUsers, updatedState.dataList) // Users should be concatenated
        assertEquals(false, updatedState.isLoading)
        assertEquals(false, updatedState.isRefreshing)
        assertEquals(false, updatedState.canLoadMore)
    }
}