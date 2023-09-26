package com.github.gasblg.firebaseapp.domain.usecases.datastore

import com.github.gasblg.firebaseapp.domain.repository.DataStoreRepository


class ClearPrefsUseCase(private val dataStoreRepository: DataStoreRepository) {

    suspend fun invoke() = dataStoreRepository.remove()

}