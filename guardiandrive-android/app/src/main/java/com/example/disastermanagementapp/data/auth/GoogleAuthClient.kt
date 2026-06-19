package com.example.disastermanagementapp.data.auth

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class GoogleAuthClient(
    private val context: Context
) {
    // You should put your WEB CLIENT ID here from the Google Cloud Console
    private val webClientId = "650827962018-hphg7f2c4umgkpucsvm5arjs3l7lfu2j.apps.googleusercontent.com"

    private val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .requestIdToken(webClientId)
        .build()

    val signInClient: GoogleSignInClient = GoogleSignIn.getClient(context, gso)
}
