package com.github.gasblg.firebaseapp.data.repository.impl

import com.github.gasblg.firebaseapp.data.utils.Constants
import com.github.gasblg.firebaseapp.data.utils.addValueEventListenerFlowList
import com.github.gasblg.firebaseapp.domain.models.Item
import com.github.gasblg.firebaseapp.domain.repository.ItemsRepository
import com.google.firebase.database.FirebaseDatabase


class ItemsRepositoryImpl(instance: FirebaseDatabase) : ItemsRepository {

    private val itemsRef = instance.getReference(Constants.ITEMS)

    override suspend fun loadItems() = itemsRef.addValueEventListenerFlowList(Item::class.java)

    override suspend fun addItem(item: Item) {
        val key = itemsRef.push().key
        key?.let {
            val value = item.copy(id = it)
            itemsRef
                .child(it)
                .setValue(value)
        }
    }

    override suspend fun removeItem(itemId: String?) {
        itemId?.let {
            itemsRef
                .child(it)
                .setValue(null)
        }
    }
}