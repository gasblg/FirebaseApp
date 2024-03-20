package com.github.gasblg.firebaseapp.domain.di

import com.github.gasblg.firebaseapp.domain.helpers.DateManager
import com.github.gasblg.firebaseapp.domain.repository.RemoteConfigRepository
import com.github.gasblg.firebaseapp.domain.repository.UserRepository
import com.github.gasblg.firebaseapp.domain.usecases.config.GetConfigSettingsUseCase
import com.github.gasblg.firebaseapp.domain.usecases.config.InitConfigSettingsUseCase
import com.github.gasblg.firebaseapp.domain.usecases.date.DateConverterUseCase
import com.github.gasblg.firebaseapp.domain.usecases.user.SaveUserUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class UseCasesModule {

    @Provides
    @Singleton
    fun provideSaveUserUseCase(userRepository: UserRepository) = SaveUserUseCase(userRepository)


    @Provides
    @Singleton
    fun provideGetConfigSettingsUseCase(remoteConfigRepository: RemoteConfigRepository) =
        GetConfigSettingsUseCase(remoteConfigRepository)

    @Provides
    @Singleton
    fun provideInitConfigSettingsUseCase(remoteConfigRepository: RemoteConfigRepository) =
        InitConfigSettingsUseCase(remoteConfigRepository)


    @Provides
    @Singleton
    fun provideDateConverterUseCase(dateManager: DateManager) =
        DateConverterUseCase(dateManager)

}
