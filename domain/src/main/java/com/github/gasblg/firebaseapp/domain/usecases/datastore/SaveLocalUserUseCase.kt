package com.github.gasblg.firebaseapp.domain.usecases.datastore

import com.github.gasblg.firebaseapp.domain.models.UserProfile
import com.github.gasblg.firebaseapp.domain.repository.DataStoreRepository


class SaveLocalUserUseCase(private val dataStoreRepository: DataStoreRepository) {

   suspend fun invoke(profile: UserProfile) = dataStoreRepository.updateUser(profile)


}