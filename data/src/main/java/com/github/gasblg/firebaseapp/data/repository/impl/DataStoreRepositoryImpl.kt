package com.github.gasblg.firebaseapp.data.repository.impl

import android.content.Context
import androidx.datastore.core.DataStore
import com.github.gasblg.firebaseapp.User
import com.github.gasblg.firebaseapp.domain.models.UserProfile
import com.github.gasblg.firebaseapp.data.serializer.userDataStore
import com.github.gasblg.firebaseapp.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class DataStoreRepositoryImpl(context: Context) : DataStoreRepository {

    private var userStore: DataStore<User> = context.userDataStore

    override fun getUserData(): Flow<UserProfile> = userStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(User.getDefaultInstance())
            } else {
                throw exception
            }
        }.map {
            UserProfile(it.uid, it.name, it.email, it.photo, it.dateCreated, it.dateSignedIn)
        }

    override suspend fun updateUser(profile: UserProfile?) {
        val user = convertToUser(profile)
        userStore.updateData {
            user ?: User.getDefaultInstance()
        }
    }

    override suspend fun remove() {
        userStore.updateData {
            it.toBuilder().clear().build()
        }
    }

    private fun convertToUser(profile: UserProfile?) = User.newBuilder()
        .setUid(profile?.uid)
        .setName(profile?.name)
        .setEmail(profile?.email)
        .setPhoto(profile?.photo)
        .setDateCreated(profile?.dateCreated)
        .setDateSignedIn(profile?.dateSignedIn)
        .build()
}