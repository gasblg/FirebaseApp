package com.github.gasblg.firebaseapp.data.di

import android.content.Context
import com.github.gasblg.firebaseapp.analytics.crashlytics.CrashlyticsReporter
import com.github.gasblg.firebaseapp.data.repository.impl.DataStoreRepositoryImpl
import com.github.gasblg.firebaseapp.data.repository.impl.ItemsRepositoryImpl
import com.github.gasblg.firebaseapp.data.repository.impl.RemoteConfigRepositoryImpl
import com.github.gasblg.firebaseapp.data.repository.impl.UserRepositoryImpl
import com.github.gasblg.firebaseapp.domain.repository.DataStoreRepository
import com.github.gasblg.firebaseapp.domain.repository.ItemsRepository
import com.github.gasblg.firebaseapp.domain.repository.RemoteConfigRepository
import com.github.gasblg.firebaseapp.domain.repository.UserRepository
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoriesModule {

    @Provides
    @Singleton
    fun provideItemRepository(instance: FirebaseDatabase): ItemsRepository {
        return ItemsRepositoryImpl(instance)
    }

    @Provides
    @Singleton
    fun provideUserRepository(instance: FirebaseDatabase): UserRepository {
        return UserRepositoryImpl(instance)
    }

    @Provides
    @Singleton
    fun provideRemoteConfigRepository(
        instance: FirebaseRemoteConfig,
        context: Context,
        crashlyticsReporter: CrashlyticsReporter
    ): RemoteConfigRepository {
        return RemoteConfigRepositoryImpl(instance, context, crashlyticsReporter)
    }

    @Provides
    @Singleton
    fun provideDataStoreRepository(context: Context): DataStoreRepository =
        DataStoreRepositoryImpl(context)
}