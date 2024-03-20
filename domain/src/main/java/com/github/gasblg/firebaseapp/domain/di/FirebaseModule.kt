package com.github.gasblg.firebaseapp.domain.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class FirebaseModule {
    @Singleton
    @Provides
    fun provideDatabase() = FirebaseDatabase.getInstance()

    @Singleton
    @Provides
    fun provideFirebaseAuthInstance() = FirebaseAuth.getInstance()

    @Singleton
    @Provides
    fun provideFirebaseRemoteConfig() = FirebaseRemoteConfig.getInstance()
}
