package com.bona.core.data.remote

import android.util.Log
import com.bona.core.data.remote.response.UserItem
import com.bona.core.data.remote.retrofit.ApiResponse
import com.bona.core.data.remote.retrofit.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {

    private inline fun <reified T> handleApiResponse(
        block: () -> T
    ): ApiResponse<T> {
        return try {
            ApiResponse.Success(block())
        } catch (e: Exception) {
            Log.e("RemoteDataSource", e.toString())
            ApiResponse.Error(e.toString())
        }
    }

    suspend fun getUsersByUsername(username: String): Flow<ApiResponse<List<UserItem>>> =
        flow {
            emit(handleApiResponse { apiService.getUsername(username).items })
        }.flowOn(Dispatchers.IO)

    suspend fun getUserDetail(username: String): Flow<ApiResponse<UserItem>> =
        flow {
            emit(handleApiResponse { apiService.getDetailUser(username) })
        }.flowOn(Dispatchers.IO)

    suspend fun getUserFollowers(username: String): Flow<ApiResponse<List<UserItem>>> =
        flow {
            emit(handleApiResponse { apiService.getFollowers(username) })
        }.flowOn(Dispatchers.IO)

    suspend fun getUserFollowing(username: String): Flow<ApiResponse<List<UserItem>>> =
        flow {
            emit(handleApiResponse { apiService.getFollowing(username) })
        }.flowOn(Dispatchers.IO)
}
