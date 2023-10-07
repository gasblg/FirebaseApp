package com.github.gasblg.firebaseapp.analytics.manager

import com.github.gasblg.firebaseapp.analytics.model.InfoModel

interface Analytics {

    fun screen(name: String, params: (() -> Map<String, Any?>)? = null)

    fun event(name: String, params: (() -> Map<String, Any?>)? = null)

    fun identify(info: InfoModel)


}