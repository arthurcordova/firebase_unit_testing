package com.proway.testapp.repository

import android.app.Activity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.common.truth.Truth.assertThat
import com.proway.testapp.repository.interfaces.ISignInResult
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.lang.Exception
import java.util.concurrent.Executor


@RunWith(JUnit4::class)
class FirebaseTest : ISignInResult {
    @Mock
    private lateinit var successTask: Task<AuthResult>
    @Mock
    private lateinit var failureTask: Task<AuthResult>
    @Mock
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var authenticationRepository: AuthenticationRepository

    private var logInResult = UNDEF

    companion object {
        private const val SUCCESS = 1
        private const val FAILURE = -1
        private const val UNDEF = 0
    }


    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        authenticationRepository = AuthenticationRepository(this, firebaseAuth)
    }


    @Test
    fun logInSuccess_test() {
        val email = "cool@cool.com"
        val password = "123456"
        Mockito.`when`(successTask.isSuccessful).thenReturn(true)
        Mockito.`when`(firebaseAuth.signInWithEmailAndPassword(email, password))
            .thenReturn(successTask)
        authenticationRepository.signIn(email, password)
        assertThat(logInResult).isEqualTo(SUCCESS)

    }

    @Test
    fun logInFailure_test() {
        val email = "cool@cool.com"
        val password = "123_456"
        Mockito.`when`(failureTask.isSuccessful).thenReturn(false)
        Mockito.`when`(firebaseAuth.signInWithEmailAndPassword(email, password))
            .thenReturn(failureTask)
        authenticationRepository.signIn(email, password)
        assertThat(logInResult).isEqualTo(FAILURE)
    }

    override fun success(email: String, password: String) {
        logInResult = SUCCESS
    }

    override fun failure(email: String, password: String) {
        logInResult = FAILURE
    }

}

