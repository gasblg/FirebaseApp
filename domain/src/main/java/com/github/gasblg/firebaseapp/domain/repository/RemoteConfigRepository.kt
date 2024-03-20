package com.github.gasblg.firebaseapp.domain.repository

interface RemoteConfigRepository {
    suspend fun init()
    fun getSettings(): Map<String, String>
}