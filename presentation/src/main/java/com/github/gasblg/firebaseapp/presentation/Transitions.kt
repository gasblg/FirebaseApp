package com.github.gasblg.firebaseapp.presentation

import android.content.Context
import android.content.Intent
import com.github.gasblg.firebaseapp.presentation.ui.activity.auth.AuthActivity
import com.github.gasblg.firebaseapp.presentation.ui.activity.main.MainActivity

object Transitions {

    fun startMain(context: Context) =
        Intent(context, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }

    fun startAuth(context: Context) =
        Intent(context, AuthActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
}