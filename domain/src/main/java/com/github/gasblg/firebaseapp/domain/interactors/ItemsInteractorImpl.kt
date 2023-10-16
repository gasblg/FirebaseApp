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

    override suspend fun loadItems(userUid: String): Flow<Response<List<Item>>> =
        loadItemsUseCase.invoke(userUid)

    override suspend fun loadItem(itemId: String, userUid: String): Flow<Response<Item>> =
        loadItemUseCase.invoke(itemId, userUid)

    override suspend fun addItem(item: Item, userUid: String) = addItemUseCase.invoke(item, userUid)

    override suspend fun editItem(item: Item, userUid: String) =
        editItemUseCase.invoke(item, userUid)

    override suspend fun removeItem(itemId: String?, userUid: String) =
        removeItemUseCase.invoke(itemId, userUid)

}