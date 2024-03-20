package com.github.gasblg.firebaseapp.domain.repository

import com.github.gasblg.firebaseapp.domain.models.UserProfile
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {

    fun getUserData(): Flow<UserProfile>
    suspend fun updateUser(profile: UserProfile?)
    suspend fun remove()
}