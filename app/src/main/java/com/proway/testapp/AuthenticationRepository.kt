package com.proway.testapp

import com.google.firebase.auth.FirebaseAuth
import com.proway.testapp.utils.ISignInResult

class AuthenticationRepository(private val observer: ISignInResult,
private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()) {

    fun signIn(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password).apply {
            if (this.isSuccessful) observer.success(email, password)
            else observer.failure(email, password)
        }
    }

}