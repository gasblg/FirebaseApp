package com.github.gasblg.firebaseapp.domain.usecases.user

import com.github.gasblg.firebaseapp.domain.models.UserProfile
import com.github.gasblg.firebaseapp.domain.repository.UserRepository


class SaveUserUseCase(private val userRepository: UserRepository) {

    fun invoke(profile: UserProfile, success: () -> Unit) =
        userRepository.saveUser(profile, success)
}