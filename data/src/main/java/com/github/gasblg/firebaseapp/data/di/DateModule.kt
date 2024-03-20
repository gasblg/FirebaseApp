package com.github.gasblg.firebaseapp.data.di

import com.github.gasblg.firebaseapp.domain.helpers.DateManager
import com.github.gasblg.firebaseapp.data.date.DateManagerImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DateModule {

    @Provides
    @Singleton
    fun provideItemRepository(): DateManager {
        return DateManagerImpl()
    }

}