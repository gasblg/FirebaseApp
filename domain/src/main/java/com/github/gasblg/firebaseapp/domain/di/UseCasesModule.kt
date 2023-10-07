package com.github.gasblg.firebaseapp.domain.di

import com.github.gasblg.firebaseapp.domain.helpers.DateManager
import com.github.gasblg.firebaseapp.domain.interactors.ItemsInteractor
import com.github.gasblg.firebaseapp.domain.interactors.ItemsInteractorImpl
import com.github.gasblg.firebaseapp.domain.repository.DataStoreRepository
import com.github.gasblg.firebaseapp.domain.repository.ItemsRepository
import com.github.gasblg.firebaseapp.domain.repository.RemoteConfigRepository
import com.github.gasblg.firebaseapp.domain.repository.UserRepository
import com.github.gasblg.firebaseapp.domain.usecases.config.GetConfigSettingsUseCase
import com.github.gasblg.firebaseapp.domain.usecases.config.InitConfigSettingsUseCase
import com.github.gasblg.firebaseapp.domain.usecases.datastore.ClearPrefsUseCase
import com.github.gasblg.firebaseapp.domain.usecases.datastore.GetLocalUserUseCase
import com.github.gasblg.firebaseapp.domain.usecases.datastore.SaveLocalUserUseCase
import com.github.gasblg.firebaseapp.domain.usecases.date.DateConverterUseCase
import com.github.gasblg.firebaseapp.domain.usecases.items.*
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
    fun provideAddItemUseCase(itemsRepository: ItemsRepository) = AddItemUseCase(itemsRepository)

    @Provides
    @Singleton
    fun provideEditItemUseCase(itemsRepository: ItemsRepository) = EditItemUseCase(itemsRepository)

    @Provides
    @Singleton
    fun provideLoadItemsUseCase(itemsRepository: ItemsRepository) =
        LoadItemsUseCase(itemsRepository)

    @Provides
    @Singleton
    fun provideLoadItemUseCase(itemsRepository: ItemsRepository) =
        LoadItemUseCase(itemsRepository)

    @Provides
    @Singleton
    fun provideRemoveItemUseCase(itemsRepository: ItemsRepository) =
        RemoveItemUseCase(itemsRepository)

    @Provides
    @Singleton
    fun provideClearPrefsUseCase(dataStoreRepository: DataStoreRepository) =
        ClearPrefsUseCase(dataStoreRepository)

    @Provides
    @Singleton
    fun provideGetLocalUserUseCase(dataStoreRepository: DataStoreRepository) =
        GetLocalUserUseCase(dataStoreRepository)

    @Provides
    @Singleton
    fun provideSaveLocalUserUseCase(dataStoreRepository: DataStoreRepository) =
        SaveLocalUserUseCase(dataStoreRepository)

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
    fun provideItemsInteractor(
        loadItemsUseCase: LoadItemsUseCase,
        loadItemUseCase: LoadItemUseCase,
        addItemUseCase: AddItemUseCase,
        editItemUseCase: EditItemUseCase,
        removeItemUseCase: RemoveItemUseCase
    ): ItemsInteractor = ItemsInteractorImpl(
        loadItemsUseCase = loadItemsUseCase,
        loadItemUseCase = loadItemUseCase,
        addItemUseCase = addItemUseCase,
        editItemUseCase = editItemUseCase,
        removeItemUseCase = removeItemUseCase
    )

    @Provides
    @Singleton
    fun provideDateConverterUseCase(dateManager: DateManager) =
        DateConverterUseCase(dateManager)

}
