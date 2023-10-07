package com.github.gasblg.firebaseapp.domain.interactors

import com.github.gasblg.firebaseapp.domain.models.Item
import com.github.gasblg.firebaseapp.domain.models.Response
import com.github.gasblg.firebaseapp.domain.usecases.items.*
import kotlinx.coroutines.flow.Flow

class ItemsInteractorImpl(
    private val loadItemsUseCase: LoadItemsUseCase,
    private val loadItemUseCase: LoadItemUseCase,
    private val addItemUseCase: AddItemUseCase,
    private val editItemUseCase: EditItemUseCase,
    private val removeItemUseCase: RemoveItemUseCase
) : ItemsInteractor {

    override suspend fun loadItems(): Flow<Response<List<Item>>> = loadItemsUseCase.invoke()

    override suspend fun loadItem(itemId: String): Flow<Response<Item>> = loadItemUseCase.invoke(itemId)

    override suspend fun addItem(item: Item) = addItemUseCase.invoke(item)

    override suspend fun editItem(item: Item) = editItemUseCase.invoke(item)

    override suspend fun removeItem(itemId: String?) = removeItemUseCase.invoke(itemId)

}