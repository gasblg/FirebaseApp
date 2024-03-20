package com.github.gasblg.firebaseapp.domain.interactors

import com.github.gasblg.firebaseapp.domain.models.Item
import com.github.gasblg.firebaseapp.domain.models.Response
import com.github.gasblg.firebaseapp.domain.repository.ItemsRepository
import kotlinx.coroutines.flow.Flow

class ItemsInteractorImpl(
    private val itemsRepository: ItemsRepository
) : ItemsInteractor {

    override suspend fun loadItems(userUid: String): Flow<Response<List<Item>>> =
        itemsRepository.loadItems(userUid)

    override suspend fun loadItem(itemId: String, userUid: String): Flow<Response<Item>> =
        itemsRepository.loadItem(itemId, userUid)

    override suspend fun addItem(item: Item, userUid: String) = itemsRepository.addItem(item, userUid)

    override suspend fun editItem(item: Item, userUid: String) =
        itemsRepository.editItem(item, userUid)

    override suspend fun removeItem(itemId: String?, userUid: String) =
        itemsRepository.removeItem(itemId, userUid)

}