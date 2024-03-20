package com.github.gasblg.firebaseapp.analytics.di

import com.github.gasblg.firebaseapp.analytics.crashlytics.CrashlyticsReporter
import com.github.gasblg.firebaseapp.analytics.crashlytics.CrashlyticsReporterImpl
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CrashlyticsModule {

    @Provides
    fun provideFirebaseCrashlytics(): FirebaseCrashlytics {
        return FirebaseCrashlytics.getInstance()
    }

    @Singleton
    @Provides
    fun provideCrashlyticsReporter(firebaseCrashlytics: FirebaseCrashlytics): CrashlyticsReporter =
        CrashlyticsReporterImpl(firebaseCrashlytics)

}