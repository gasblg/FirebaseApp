package com.github.gasblg.firebaseapp.domain.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import dagger.Module
import dagger.Provides


@Module
class FirebaseModule {

    @Provides
    fun provideDatabase() = FirebaseDatabase.getInstance()

    @Provides
    fun provideFirebaseAuthInstance() = FirebaseAuth.getInstance()

    @Provides
    fun provideFirebaseRemoteConfig() = FirebaseRemoteConfig.getInstance()
}
