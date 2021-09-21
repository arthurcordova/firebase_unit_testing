package com.proway.testapp.repository

import com.google.common.truth.Truth.assertThat
import com.proway.testapp.model.GitUserModel
import com.proway.testapp.repository.interfaces.IGithubUsersResult
import com.proway.testapp.services.api.GithubAPI
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

@RunWith(JUnit4::class)
class GithubRepositoryTest : IGithubUsersResult {

    private lateinit var successResult: Response<List<GitUserModel>>
    private lateinit var failureResult: Response<List<GitUserModel>>
    private lateinit var githubRepository: GithubRepository

    @Mock
    lateinit var api: GithubAPI

    private var RESULT = UNDEFINED

    companion object {
        private const val SUCCESS = 1
        private const val FAILURE = -1
        private const val UNDEFINED = 0
    }

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        successResult = Response.success(200, listOf(GitUserModel("aaaa", "bbbb")))
        failureResult = Response.error(500, ResponseBody.create(null, "content"))
        githubRepository = GithubRepository(api, this)
    }

    @Test
    fun `fetch user should return a success response`() = runBlocking {
        Mockito.`when`(api.fetchUsers())
            .thenReturn(successResult)
        githubRepository.fetchUsers()
        assertThat(RESULT).isEqualTo(SUCCESS)
    }

    @Test
    fun `fetch user should return a failure response`() = runBlocking {
        Mockito.`when`(api.fetchUsers())
            .thenReturn(failureResult)
        githubRepository.fetchUsers()
        assertThat(RESULT).isEqualTo(FAILURE)
    }

    override fun success() {
        RESULT = SUCCESS
    }

    override fun failure() {
        RESULT = FAILURE
    }
}