package com.proway.testapp.repository

import com.proway.testapp.repository.interfaces.IGithubUsersResult
import com.proway.testapp.services.api.GithubAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GithubRepository(private val api: GithubAPI, private val observer: IGithubUsersResult) {

    suspend fun fetchUsers() {
        val response = withContext(Dispatchers.Default) {
            api.fetchUsers()
        }
        if (response.isSuccessful) observer.success() else observer.failure()
    }

}