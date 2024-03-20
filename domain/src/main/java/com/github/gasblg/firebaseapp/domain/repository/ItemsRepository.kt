package com.github.gasblg.firebaseapp.domain.repository

import com.github.gasblg.firebaseapp.domain.models.Item
import com.github.gasblg.firebaseapp.domain.models.Response
import kotlinx.coroutines.flow.Flow

interface ItemsRepository {

    suspend fun loadItems(userUid: String): Flow<Response<List<Item>>>

    suspend fun loadItem(itemId:String, userUid: String): Flow<Response<Item>>

    suspend fun addItem(item: Item, userUid: String)

    suspend fun editItem(item: Item, userUid: String)

    suspend fun removeItem(itemId: String?, userUid: String)

}