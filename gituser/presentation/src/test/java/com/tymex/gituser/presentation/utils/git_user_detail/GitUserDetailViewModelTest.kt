package com.tymex.gituser.presentation.utils.git_user_detail

import com.pome.test.MainCoroutineExtension
import com.tymex.core.domain.git_user.GitUser
import com.tymex.core.domain.util.DataError
import com.tymex.core.domain.util.Result
import com.tymex.core.presentation.ui.UiText
import com.tymex.gituser.domain.GitUserRepository
import com.tymex.gituser.presentation.git_user_detail.GitUserDetailEvent
import com.tymex.gituser.presentation.git_user_detail.GitUserDetailViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.CoroutineScope
import com.tymex.core.domain.util.Result.Success
import com.tymex.core.domain.util.Result.Error
import io.mockk.every
import io.mockk.spyk
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

class GitUserDetailViewModelTest {

    companion object {
        @JvmField
        @RegisterExtension
        val mainCoroutineExtension = MainCoroutineExtension()
    }

    private lateinit var viewModel: GitUserDetailViewModel

    private lateinit var gitUserRepository: GitUserRepository

    private lateinit var testDispatcher: TestDispatcher

    private lateinit var testScope: CoroutineScope

    @BeforeEach
    fun setup() {
        testDispatcher = mainCoroutineExtension.testDispatcher
        testScope = CoroutineScope(testDispatcher)

        gitUserRepository = mockk()
    }

    @Test
    fun `init sets loading, fetches user, and updates state on success`() = runTest {
        // Given
        val mockGitUser = GitUser(id = 1L, login = "mockLogin", followers = 100L, following = 50L )
        coEvery { gitUserRepository.fetchGitUserByGitUserName("mockLogin") } returns com.tymex.core.domain.util.Result.Success(mockGitUser)

        // When
        viewModel = GitUserDetailViewModel( gitUserLogin = "mockLogin", gitUserRepository =  gitUserRepository)
        advanceUntilIdle() // Wait for coroutine to complete

        // Then
        val state = viewModel.state
        assertEquals(mockGitUser, state.gitUser)
        assertEquals(100L, state.followerNumber)
        assertEquals(50L, state.followingNumber)
        assertEquals(false, state.isLoading)
    }

    @Test
    fun `init sets loading, sends error event, and stops loading on failure`() = runTest {
        // Given
        coEvery { gitUserRepository.fetchGitUserByGitUserName("mockLogin") } returns Result.Error(DataError.Network.SERVER_ERROR)

        // When
        viewModel = GitUserDetailViewModel(gitUserRepository = gitUserRepository, gitUserLogin ="mockLogin")

        // Use a separate coroutine to collect events from evenChannel
        val eventList = mutableListOf<GitUserDetailEvent>()
        val job = launch {
            viewModel.events.collect { event ->
                eventList.add(event)
            }
        }

        advanceUntilIdle() // Wait for coroutine to complete

        // Then
        val state = viewModel.state
        assertEquals(null, state.gitUser)
        assertEquals(false, state.isLoading)

        // Verify that the error event was sent
        assert(eventList.contains(GitUserDetailEvent.Error(UiText.DynamicString("Unknown Error"))))
        job.cancel()
    }

}