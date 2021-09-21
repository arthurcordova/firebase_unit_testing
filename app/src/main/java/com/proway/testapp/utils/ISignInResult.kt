package com.proway.testapp.utils

interface ISignInResult {
    fun success(email: String, password: String)
    fun failure(email: String, password: String)
}
