package com.github.gasblg.firebaseapp.analytics.di

import android.content.Context
import com.github.gasblg.firebaseapp.analytics.crashlytics.CrashlyticsReporter
import com.github.gasblg.firebaseapp.analytics.firebase.FirebaseTracker
import com.github.gasblg.firebaseapp.analytics.manager.AnalyticsManager
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AnalyticsModule {

    @Singleton
    @Provides
    fun provideFirebaseAnalytics(context: Context): FirebaseAnalytics {
        return FirebaseAnalytics.getInstance(context)
    }

    @Singleton
    @Provides
    fun provideFirebaseTracker(
        context: Context,
        firebase: FirebaseAnalytics,
        crashlytics: CrashlyticsReporter
    ) = FirebaseTracker(context, firebase, crashlytics)

    @Singleton
    @Provides
    fun provideAnalyticsManager(firebase: FirebaseTracker) = AnalyticsManager(firebase)

}