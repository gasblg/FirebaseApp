package com.github.gasblg.firebaseapp.domain.usecases.config

import com.github.gasblg.firebaseapp.domain.repository.RemoteConfigRepository


class InitConfigSettingsUseCase(private val remoteConfigRepository: RemoteConfigRepository) {

    suspend fun invoke() = remoteConfigRepository.init()

}