package com.github.gasblg.firebaseapp.domain.models

sealed class Response<out T> {
    data object Loading : Response<Nothing>()
    data object Empty : Response<Nothing>()
    data class Success<out T>(val data: T) : Response<T>()
    data class Failure(val e: Throwable) : Response<Nothing>()
}