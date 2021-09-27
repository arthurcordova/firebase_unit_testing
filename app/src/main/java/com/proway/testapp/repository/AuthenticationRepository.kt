package com.proway.testapp.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.proway.testapp.repository.interfaces.ISignInResult

class AuthenticationRepository(private val observer: ISignInResult,
                               private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()) {

    fun signIn(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password).apply {
            if (this.isSuccessful) observer.success(email, password)
            else observer.failure(email, password)
        }
    }

    fun signInWithCallback(email: String, password: String, callback: (FirebaseUser?, String?) -> Unit) {
        firebaseAuth.signInWithEmailAndPassword(email, password).apply {
            if (this.isSuccessful) callback(result?.user, null)
            else callback(null, "Erro diferente")
        }
    }

}