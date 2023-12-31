package com.github.gasblg.firebaseapp.domain.usecases.items

import com.github.gasblg.firebaseapp.domain.models.Item
import com.github.gasblg.firebaseapp.domain.repository.ItemsRepository


class EditItemUseCase(private val itemsRepository: ItemsRepository) {

    suspend fun invoke(item: Item, userUid: String) = itemsRepository.editItem(item, userUid)
}