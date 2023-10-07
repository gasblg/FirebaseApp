package com.github.gasblg.firebaseapp.domain.interactors

import com.github.gasblg.firebaseapp.domain.models.Item
import com.github.gasblg.firebaseapp.domain.models.Response
import kotlinx.coroutines.flow.Flow

interface ItemsInteractor {

    suspend fun loadItems(): Flow<Response<List<Item>>>

    suspend fun loadItem(itemId: String): Flow<Response<Item>>

    suspend fun addItem(item: Item)

    suspend fun editItem(item: Item)

    suspend fun removeItem(itemId: String?)
}