package com.tymex.core.database

import com.tymex.core.database.dao.GitUserDao
import com.tymex.core.database.entity.GitUserEntity
import com.tymex.core.database.mappers.toGitUser
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class RoomLocalGitUserDataSourceTest {

    // Mock objects
    private lateinit var gitUserDao: GitUserDao
    private lateinit var dataSource: RoomLocalGitUserDataSource

    @BeforeEach
    fun setup() {
        gitUserDao = mockk()
        dataSource = RoomLocalGitUserDataSource(gitUserDao)
    }

    @Test
    fun `getGitUsers returns transformed git users from dao`() = runTest {
        // Given
        val gitUserEntities = listOf(
            GitUserEntity(
                id = 1L,
                login = "User1",
                avatarUrl = "avatar_url_1",
                htmlUrl = "html_1",
                name = "name 1",
                location = "location 1",
                blog = "blog 1",
                following = 1L,
                followers = 100L,
                localId = "hax",
            ),
            GitUserEntity(
                id = 2L,
                login = "User2",
                avatarUrl = "avatar_url_2",
                htmlUrl = "html_2",
                name = "name 2",
                location = "location 2",
                blog = "blog 2",
                following = 2L,
                followers = 200L,
                localId = "hax 2",
            )
        )
        val gitUsers = gitUserEntities.map { it.toGitUser() }

        // Mock DAO behavior
        every { gitUserDao.getGitUsers() } returns flowOf(gitUserEntities)

        // When
        val result = dataSource.getGitUsers().first()

        // Then
        assertEquals(gitUsers, result) // Verify that transformation worked
    }
}