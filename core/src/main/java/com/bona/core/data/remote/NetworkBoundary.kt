package com.bona.core.data.remote

import com.bona.core.data.remote.retrofit.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.flow

abstract class NetworkBoundary<ResultType, RequestType> {

    private val result: Flow<ItemState<ResultType>> = flow {
        emit(ItemState.Loading())
        when (val apiResponse = createCall().first()) {
            is ApiResponse.Success -> {
                emitAll(loadFromNetwork(apiResponse.data).map { ItemState.Success(it) })
            }
            is ApiResponse.Empty -> {
                (ItemState.Success(null))
            }
            is ApiResponse.Error -> {
                onFetchFailed()
                emit(ItemState.Error(apiResponse.errorMessage))
            }
        }
    }

    protected open fun onFetchFailed() {}

    protected abstract fun loadFromNetwork(data: RequestType): Flow<ResultType>

    protected abstract suspend fun createCall(): Flow<ApiResponse<RequestType>>

    fun asFlow(): Flow<ItemState<ResultType>> = result
}
