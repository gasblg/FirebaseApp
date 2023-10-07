package com.github.gasblg.firebaseapp.analytics.model

data class EventModel(
    val name: String,
    val params: Map<String, Any?>? = null
)
