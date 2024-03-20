package com.github.gasblg.firebaseapp.domain.di

import com.github.gasblg.firebaseapp.domain.interactors.ItemsInteractor
import com.github.gasblg.firebaseapp.domain.interactors.ItemsInteractorImpl
import com.github.gasblg.firebaseapp.domain.repository.ItemsRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class InteractorModule {

    @Provides
    @Singleton
    fun provideItemsInteractor(
        itemsRepository: ItemsRepository
    ): ItemsInteractor = ItemsInteractorImpl(itemsRepository)

}
