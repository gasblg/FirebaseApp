package com.github.gasblg.firebaseapp.domain.managers

import com.github.gasblg.firebaseapp.domain.models.UserProfile
import com.github.gasblg.firebaseapp.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.firstOrNull

class UserManager(private val dataStoreRepository: DataStoreRepository) {

    suspend fun saveUser(profile: UserProfile) = dataStoreRepository.updateUser(profile)


    suspend fun getLocalUserData() = dataStoreRepository.getUserData().firstOrNull()


    suspend fun clearUser() = dataStoreRepository.remove()
}