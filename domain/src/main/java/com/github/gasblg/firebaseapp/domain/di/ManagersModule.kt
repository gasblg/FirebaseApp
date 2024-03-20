package com.github.gasblg.firebaseapp.domain.di

import com.github.gasblg.firebaseapp.domain.managers.UserManager
import com.github.gasblg.firebaseapp.domain.repository.DataStoreRepository

import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class ManagersModule {

    @Provides
    @Singleton
    fun provideUserManager(dataStoreRepository: DataStoreRepository) =
        UserManager(dataStoreRepository)


}
