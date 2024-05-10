package com.bona.core.data.local

import kotlinx.coroutines.flow.Flow

class LocalDataSource(
    private val item: ItemUserDao,
) {
    fun getAllUserFavorite(): Flow<List<ItemUserEntity>> = item.getAllUserFavorite()

    suspend fun insertUserFavorite(user: ItemUserEntity) = item.insertUserFavorite(user)

    suspend fun deleteUserFavorite(user: ItemUserEntity) = item.deleteUserFavorite(user)

    fun getFavorite(username: String): Flow<Boolean> = item.getFavorite(username)
}