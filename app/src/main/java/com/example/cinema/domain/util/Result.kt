package com.example.cinema.domain.util

sealed class Result<T>(val data :T? = null, val message: String? = null) {
    class Loading<T> : Result<T>()
    class Error<T>(message: String? = null, data: T? = null) : Result<T>(data, message)
    class Success<T>(data: T) : Result<T>(data)
}