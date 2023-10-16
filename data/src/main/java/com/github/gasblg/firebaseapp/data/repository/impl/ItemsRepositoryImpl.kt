package com.github.gasblg.firebaseapp.data.repository.impl

import com.github.gasblg.firebaseapp.data.utils.*
import com.github.gasblg.firebaseapp.domain.models.Item
import com.github.gasblg.firebaseapp.domain.repository.ItemsRepository
import com.google.firebase.database.FirebaseDatabase


class ItemsRepositoryImpl(val instance: FirebaseDatabase) : ItemsRepository {

    private val itemsRef = instance.getReference(Constants.ITEMS)

    override suspend fun loadItems(userUid: String) =
        itemsRef.child(userUid).getEventListenerFlowList(Item::class.java)

    override suspend fun loadItem(itemId: String, userUid: String) = itemsRef
        .child(userUid)
        .child(itemId).getEventListenerFlow(Item::class.java)

    override suspend fun addItem(item: Item, userUid: String) {
        val key = itemsRef.push().key
        key?.let {
            val value = item.copy(id = it)
            itemsRef
                .child(userUid)
                .child(it)
                .setValue(value)
        }
    }

    override suspend fun editItem(item: Item, userUid: String) {
        itemsRef
            .child(userUid)
            .child(item.id)
            .setValue(item)
    }

    override suspend fun removeItem(itemId: String?, userUid: String) {
        itemId?.let {
            itemsRef
                .child(userUid)
                .child(it).removeValue()
        }
    }
}