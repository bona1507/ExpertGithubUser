package com.bona.core.data.remote

sealed class ItemState<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : ItemState<T>(data)
    class Loading<T>(data: T? = null) : ItemState<T>(data)
    class Error<T>(message: String, data: T? = null) : ItemState<T>(data, message)
}