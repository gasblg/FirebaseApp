package com.github.gasblg.firebaseapp.analytics.crashlytics

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.github.gasblg.firebaseapp.analytics.BuildConfig

class CrashlyticsReporterImpl(
    val crashlytics: FirebaseCrashlytics
) : CrashlyticsReporter {


    override fun error(tag: String, error: Throwable) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, error.message ?: "")
        } else {
            crashlytics.recordException(error)
        }
    }

    override fun setCollectionEnabled(enabled: Boolean) {
        crashlytics.setCrashlyticsCollectionEnabled(enabled && !BuildConfig.DEBUG)
    }

    override fun log(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, message)
        } else {
            crashlytics.log(message)
        }
    }
}