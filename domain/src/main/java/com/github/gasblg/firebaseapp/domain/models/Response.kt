package com.github.gasblg.firebaseapp.domain.models

sealed class Response<out T> {
    object Loading : Response<Nothing>()
    object Empty : Response<Nothing>()
    data class Success<out T>(val data: T) : Response<T>()
    data class Failure(val e: Throwable) : Response<Nothing>()
}