package com.proway.testapp.services.api

import com.proway.testapp.model.GitUserModel
import retrofit2.Response
import retrofit2.http.GET

interface GithubAPI {

    @GET("/users")
    suspend fun fetchUsers(
    ): Response<List<GitUserModel>>

}