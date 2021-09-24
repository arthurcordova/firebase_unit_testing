package com.proway.testapp.repository

import com.google.android.gms.tasks.Task
import com.google.common.truth.Truth.assertThat
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.proway.testapp.model.GitUserModel
import com.proway.testapp.repository.interfaces.IGithubUsersResult
import com.proway.testapp.services.api.GithubAPI
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.After
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

    private lateinit var successResultApi: Response<List<GitUserModel>>
    private lateinit var failureResultApi: Response<List<GitUserModel>>
    private lateinit var githubRepository: GithubRepository

    @Mock
    lateinit var successResultFirebase: Task<DocumentReference>
    @Mock
    lateinit var failureResultFirebase: Task<DocumentReference>
    @Mock
    lateinit var api: GithubAPI
    @Mock
    lateinit var firestore: FirebaseFirestore
    @Mock
    lateinit var collectionReference: CollectionReference

    private var RESULT = UNDEFINED

    companion object {
        private const val SUCCESS = 1
        private const val FAILURE = -1
        private const val UNDEFINED = 0
    }

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        successResultApi = Response.success(200, listOf(GitUserModel("aaaa", "bbbb")))
        failureResultApi = Response.error(500, ResponseBody.create(null, "content"))
        githubRepository = GithubRepository(api, this, firestore)
    }

    @After
    fun tearDown() {
        RESULT = UNDEFINED
    }

    @Test
    fun `fetch user should return a success response`() = runBlocking {
        Mockito.`when`(api.fetchUsers())
            .thenReturn(successResultApi)
        githubRepository.fetchUsers()
        assertThat(RESULT).isEqualTo(SUCCESS)
    }

    @Test
    fun `fetch user should return a failure response`() = runBlocking {
        Mockito.`when`(api.fetchUsers())
            .thenReturn(failureResultApi)
        githubRepository.fetchUsers()
        assertThat(RESULT).isEqualTo(FAILURE)
    }

    @Test
    fun `fetch user should return a failure response with closure`() = runBlocking {
        val callback : (String) -> Unit = {
            assertThat(it).isEqualTo("Failure")
        }
        Mockito.`when`(api.fetchUsers())
            .thenReturn(failureResultApi)
        githubRepository.fetchUsersWithCallBack(callback)
    }

    @Test
    fun `save user should return a success response`() = runBlocking {
        val userToSave = GitUserModel("aa", "ccc")
        Mockito.`when`(successResultFirebase.isSuccessful).thenReturn(true)
        Mockito.`when`(firestore.collection(GithubRepository.COLLECTION_NAME))
            .thenReturn(collectionReference)
        Mockito.`when`(collectionReference.add(userToSave))
            .thenReturn(successResultFirebase)
        githubRepository.saveUserOnFirebase(userToSave)
        assertThat(RESULT).isEqualTo(SUCCESS)
    }

    @Test
    fun `save user should return a failure response`() = runBlocking {
        val userToSave = GitUserModel("aa", "ccc")
        Mockito.`when`(failureResultFirebase.isSuccessful).thenReturn(false)
        Mockito.`when`(firestore.collection(GithubRepository.COLLECTION_NAME))
            .thenReturn(collectionReference)
        Mockito.`when`(collectionReference.add(userToSave))
            .thenReturn(failureResultFirebase)
        githubRepository.saveUserOnFirebase(userToSave)
        assertThat(RESULT).isEqualTo(FAILURE)
    }

    override fun successApi() {
        RESULT = SUCCESS
    }

    override fun failureApi() {
        RESULT = FAILURE
    }

    override fun successFirebase() {
        RESULT = SUCCESS
    }

    override fun failureFirebase() {
        RESULT = FAILURE
    }
}