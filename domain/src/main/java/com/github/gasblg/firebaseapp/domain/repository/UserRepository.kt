package com.github.gasblg.firebaseapp.domain.repository

import com.github.gasblg.firebaseapp.domain.models.UserProfile

interface UserRepository {
    fun saveUser(profile: UserProfile, success: () -> Unit)

}