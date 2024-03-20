package com.github.gasblg.firebaseapp.analytics.manager

import com.github.gasblg.firebaseapp.analytics.firebase.FirebaseTracker
import com.github.gasblg.firebaseapp.analytics.data.*

class AnalyticsManager(firebaseTracker: FirebaseTracker) : AnalyticsCreator(firebaseTracker){

    fun login() = event(Event.LOGIN)

    fun logout() = event(Event.LOGOUT)

    fun openMainScreen() = screen(Screen.MAIN_SCREEN)

    fun openDetailScreen() = screen(Screen.DETAIL_SCREEN)

    fun openAddDialog() = screen(Screen.ADD_DIALOG)

    fun openEditDialog() = screen(Screen.EDIT_DIALOG)

    fun openRemoveDialog() = screen(Screen.REMOVE_DIALOG)

    fun itemAddTap() = event(Event.ITEM_ADD_TAP)

    fun itemLogoutTap() = event(Event.ITEM_LOGOUT_TAP)

    fun itemTap(event: String, name: String, description: String, position: Int) = event(event) {
        mapOf(
            Param.NAME to name,
            Param.DESCRIPTION to description,
            Param.POSITION to position + 1
        )
    }

    fun actionTap(event: String, action: String) = event(event) {
        mapOf(
            Param.ACTION to action
        )
    }
}