package com.proway.testapp.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.proway.testapp.model.GitUserModel
import com.proway.testapp.repository.interfaces.IGithubUsersResult
import com.proway.testapp.services.api.GithubAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GithubRepository(
    private val api: GithubAPI,
    private val observer: IGithubUsersResult,
    private val firebaseDb : FirebaseFirestore
) {
    companion object {
        const val COLLECTION_NAME = "github_users"
    }


    suspend fun fetchUsers() {
        val response = withContext(Dispatchers.Default) {
            api.fetchUsers()
        }
        if (response.isSuccessful) observer.successApi() else observer.failureApi()
    }

    suspend fun fetchUsersWithCallBack(onComplete: (String) -> Unit) {
        val response = withContext(Dispatchers.Default) {
            api.fetchUsers()
        }
        if (response.isSuccessful) onComplete("Success") else onComplete("Failure")
    }

    fun saveUserOnFirebase(user: GitUserModel) {
        val task = firebaseDb.collection(COLLECTION_NAME).add(user)
        if (task.isSuccessful) observer.successFirebase() else observer.failureApi()
    }

}