package com.github.gasblg.firebaseapp.domain.usecases.items

import com.github.gasblg.firebaseapp.domain.repository.ItemsRepository


class RemoveItemUseCase(private val itemsRepository: ItemsRepository) {

    suspend fun invoke(itemId: String?) = itemsRepository.removeItem(itemId)
}