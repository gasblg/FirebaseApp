package com.github.gasblg.firebaseapp.analytics.manager

import com.github.gasblg.firebaseapp.analytics.firebase.FirebaseTracker
import com.github.gasblg.firebaseapp.analytics.model.EventModel
import com.github.gasblg.firebaseapp.analytics.model.InfoModel

open class AnalyticsCreator(
    private val firebaseTracker: FirebaseTracker
) : Analytics {

    override fun screen(name: String, params: (() -> Map<String, Any?>)?) {
        val event = withParams(name, params)
        firebaseTracker.screen(event)
    }

    override fun event(name: String, params: (() -> Map<String, Any?>)?) {
        val event = withParams(name, params)
        firebaseTracker.event(event)
    }

    private fun withParams(name: String, params: (() -> Map<String, Any?>)? = null): EventModel {
        val map = params?.invoke()
        val finalParams = (map ?: mapOf())
        return EventModel(name, finalParams)
    }

    override fun identify(info: InfoModel) {
        firebaseTracker.identify(info)
    }

}