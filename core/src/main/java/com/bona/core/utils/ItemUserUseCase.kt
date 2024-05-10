package com.bona.core.utils

import com.bona.core.data.remote.ItemState
import kotlinx.coroutines.flow.Flow

interface ItemUserUseCase {

    fun getUsername(username: String): Flow<ItemState<List<ItemUser>>>

    fun getUserDetail(username: String): Flow<ItemState<ItemUser>>

    fun getUserFollowers(username: String): Flow<ItemState<List<ItemUser>>>

    fun getUserFollowing(username: String): Flow<ItemState<List<ItemUser>>>

    fun getAllUserFavorite(): Flow<List<ItemUser>>

    suspend fun insertUserFavorite(users: ItemUser)

    suspend fun deleteUserFavorite(users: ItemUser)

    fun getFavorite(username: String): Flow<Boolean>
}