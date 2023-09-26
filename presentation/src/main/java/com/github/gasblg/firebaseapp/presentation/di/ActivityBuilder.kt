package com.github.gasblg.firebaseapp.presentation.di

import android.app.Application
import android.content.Context
import com.github.gasblg.firebaseapp.presentation.di.fragments.FragmentsBuilder
import com.github.gasblg.firebaseapp.presentation.di.fragments.scopes.ActivitiesScope
import com.github.gasblg.firebaseapp.presentation.ui.activity.auth.AuthActivity
import com.github.gasblg.firebaseapp.presentation.ui.activity.main.MainActivity
import dagger.Binds

import dagger.Module
import dagger.android.ContributesAndroidInjector
import javax.inject.Singleton


@Module
abstract class ActivityBuilder {

    @Binds
    @Singleton
    internal abstract fun application(app: Application): Context

    @ActivitiesScope
    @ContributesAndroidInjector(modules = [FragmentsBuilder::class])
    internal abstract fun contributeAuthActivity(): AuthActivity

    @ActivitiesScope
    @ContributesAndroidInjector(modules = [FragmentsBuilder::class])
    internal abstract fun contributeMainActivity(): MainActivity
}
