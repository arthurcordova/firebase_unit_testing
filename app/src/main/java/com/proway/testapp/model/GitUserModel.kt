package com.proway.testapp.model

import com.google.gson.annotations.SerializedName

data class GitUserModel(
    @SerializedName("login")
    val login: String?,
    @SerializedName("avatar_url")
    val avatar: String?
)
