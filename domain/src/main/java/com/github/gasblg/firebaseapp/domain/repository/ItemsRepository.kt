package com.github.gasblg.firebaseapp.domain.repository

import com.github.gasblg.firebaseapp.domain.models.Item
import com.github.gasblg.firebaseapp.domain.models.Response
import kotlinx.coroutines.flow.Flow

interface ItemsRepository {

    suspend fun loadItems(): Flow<Response<List<Item>>>

    suspend fun loadItem(itemId:String): Flow<Response<Item>>

    suspend fun addItem(item: Item)

    suspend fun editItem(item: Item)

    suspend fun removeItem(itemId: String?)

}