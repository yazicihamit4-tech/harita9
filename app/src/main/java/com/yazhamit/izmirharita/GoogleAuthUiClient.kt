package com.yazhamit.izmirharita

import android.content.Context
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class GoogleAuthUiClient(private val context: Context) {

    private val auth = FirebaseAuth.getInstance()
    private val webClientId = "420998117364-k7ur119cufa2ijm1vc2d7hsjsll2jpcd.apps.googleusercontent.com"

    fun getGoogleSignInClient(): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(webClientId)
            .requestEmail()
            .build()
        return GoogleSignIn.getClient(context, gso)
    }

    fun getSignedInUser(): FirebaseUser? {
        return auth.currentUser
    }

    fun signOut(onComplete: () -> Unit) {
        auth.signOut()
        getGoogleSignInClient().signOut().addOnCompleteListener {
            onComplete()
        }
    }
}
