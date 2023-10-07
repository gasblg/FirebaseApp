package com.github.gasblg.firebaseapp.analytics.firebase

import android.content.Context
import android.os.Bundle
import androidx.core.os.bundleOf
import com.github.gasblg.firebaseapp.analytics.crashlytics.CrashlyticsReporter
import com.github.gasblg.firebaseapp.analytics.data.User
import com.github.gasblg.firebaseapp.analytics.model.EventModel
import com.github.gasblg.firebaseapp.analytics.model.InfoModel
import com.google.firebase.analytics.FirebaseAnalytics

class FirebaseTracker(
    val context: Context,
    private val firebase: FirebaseAnalytics,
    private val crashlytics: CrashlyticsReporter
) : AnalyticsTracker {

    override fun event(eventModel: EventModel) {
        crashlytics.setCollectionEnabled(true)
        firebase.logEvent(eventModel.name, eventModel.params?.let { toBundle(it) })
    }

    override fun screen(eventModel: EventModel) {
        event(eventModel)
    }

    private fun toBundle(map: Map<String, Any?>): Bundle {
        val pairs = map.entries.map { it.key to it.value }
        return bundleOf(*pairs.toTypedArray())
    }

    override fun identify(info: InfoModel) {
        val userId = info.userId
        val dateCreated = info.dateCreated
        val dateSignedIn = info.dateSignedIn

        with(firebase) {
            setUserId(userId)
            setUserProperty(User.ID, userId)
            setUserProperty(User.DATE_CREATED, dateCreated)
            setUserProperty(User.DATE_SIGNED_IN, dateSignedIn)
        }
    }

}