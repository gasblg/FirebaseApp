package com.github.gasblg.firebaseapp.analytics.firebase

import com.github.gasblg.firebaseapp.analytics.model.EventModel
import com.github.gasblg.firebaseapp.analytics.model.InfoModel

internal interface AnalyticsTracker {

    fun event(eventModel: EventModel)

    fun screen(eventModel: EventModel)

    fun identify(info: InfoModel)

}