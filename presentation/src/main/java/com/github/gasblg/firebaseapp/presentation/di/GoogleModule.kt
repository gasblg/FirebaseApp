package com.github.gasblg.firebaseapp.presentation.di

import android.content.Context
import com.github.gasblg.firebaseapp.presentation.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.Module
import dagger.Provides


@Module
class GoogleModule {

    @Provides
    fun googleSignInOptions(context: Context): GoogleSignInOptions {
        return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
    }

    @Provides
    fun googleSignInClient(context: Context, googleSignInOptions: GoogleSignInOptions) =
        GoogleSignIn.getClient(context, googleSignInOptions)

}