package com.proway.testapp.repository.interfaces

interface ISignInResult {
    fun success(email: String, password: String)
    fun failure(email: String, password: String)
}
