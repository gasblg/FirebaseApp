package com.github.gasblg.firebaseapp.domain.usecases.items

import com.github.gasblg.firebaseapp.domain.repository.ItemsRepository


class LoadItemUseCase(private val itemsRepository: ItemsRepository) {

    suspend fun invoke(itemId: String) = itemsRepository.loadItem(itemId)
}