package com.github.gasblg.firebaseapp.domain.usecases.datastore

import com.github.gasblg.firebaseapp.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.firstOrNull


class GetLocalUserUseCase(private val dataStoreRepository: DataStoreRepository) {

   suspend fun invoke() = dataStoreRepository.getUserData().firstOrNull()

}