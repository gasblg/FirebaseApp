package com.github.gasblg.firebaseapp.analytics.crashlytics


interface CrashlyticsReporter {

    fun error(tag: String, error: Throwable)

    fun setCollectionEnabled(enabled: Boolean)

    fun log(tag: String, message: String)
}