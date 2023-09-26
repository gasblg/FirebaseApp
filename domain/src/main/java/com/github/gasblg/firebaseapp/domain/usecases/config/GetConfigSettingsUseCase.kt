package com.github.gasblg.firebaseapp.domain.usecases.config

import com.github.gasblg.firebaseapp.domain.repository.RemoteConfigRepository


class GetConfigSettingsUseCase(private val remoteConfigRepository: RemoteConfigRepository) {

    fun invoke() = remoteConfigRepository.getSettings()

}