package com.github.gasblg.firebaseapp.data.utils

import com.github.gasblg.firebaseapp.domain.models.Response
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun <T> DatabaseReference.getEventListenerFlowList(
    dataType: Class<T>
): Flow<Response<List<T>>> = callbackFlow {
    trySend(Response.Loading)
    val listener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            val list = mutableListOf<T>()
            if (dataSnapshot.exists()) {
                dataSnapshot.children.forEach {
                    val item = it.getValue(dataType)
                    if (item != null) {
                        list.add(item)
                    }
                }
                trySend(Response.Success(list))
            } else {
                trySend(Response.Empty)
            }
        }

        override fun onCancelled(error: DatabaseError) {
            trySend(Response.Failure(error.toException()))
            cancel()
        }
    }
    addValueEventListener(listener)
    awaitClose { removeEventListener(listener) }
}


fun <T> DatabaseReference.getEventListenerFlow(
    dataType: Class<T>
): Flow<Response<T>> = callbackFlow {
    trySend(Response.Loading)
    val listener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            if (dataSnapshot.exists()) {
                val item = dataSnapshot.getValue(dataType)
                item?.let {
                    trySend(Response.Success(it))
                }
            } else {
                trySend(Response.Empty)
            }
        }

        override fun onCancelled(error: DatabaseError) {
            trySend(Response.Failure(error.toException()))
            cancel()
        }
    }
    addValueEventListener(listener)
    awaitClose { removeEventListener(listener) }
}
