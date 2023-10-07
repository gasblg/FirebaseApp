package com.github.gasblg.firebaseapp.presentation.di.fragments

import com.github.gasblg.firebaseapp.presentation.ui.fragments.detail.DetailFragment
import com.github.gasblg.firebaseapp.presentation.ui.fragments.main.MainFragment
import com.github.gasblg.firebaseapp.presentation.ui.fragments.dialogs.AddDialog
import com.github.gasblg.firebaseapp.presentation.ui.fragments.dialogs.RemoveDialog
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentsBuilder {

    @ContributesAndroidInjector
    abstract fun contributeMainFragment(): MainFragment

    @ContributesAndroidInjector
    abstract fun contributeDetailFragment(): DetailFragment

    @ContributesAndroidInjector
    abstract fun contributeAddDialog(): AddDialog

    @ContributesAndroidInjector
    abstract fun contributeDeleteDialog(): RemoveDialog
}