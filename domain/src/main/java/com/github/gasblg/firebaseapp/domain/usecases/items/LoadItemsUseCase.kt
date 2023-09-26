package com.github.gasblg.firebaseapp.domain.usecases.items

import com.github.gasblg.firebaseapp.domain.repository.ItemsRepository


class LoadItemsUseCase(private val itemsRepository: ItemsRepository) {

    suspend fun invoke() = itemsRepository.loadItems()
}