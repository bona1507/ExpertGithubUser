package com.bona.core.utils

import com.bona.core.data.remote.ItemState
import kotlinx.coroutines.flow.Flow

class ItemUserInteractor(
    private val userRepository: ItemUserRepository,
) : ItemUserUseCase {
    override fun getUsername(username: String): Flow<ItemState<List<ItemUser>>> =
        userRepository.getUsername(username)

    override fun getUserDetail(username: String): Flow<ItemState<ItemUser>> =
        userRepository.getUserDetail(username)

    override fun getUserFollowers(username: String): Flow<ItemState<List<ItemUser>>> =
        userRepository.getUserFollowers(username)

    override fun getUserFollowing(username: String): Flow<ItemState<List<ItemUser>>> =
        userRepository.getUserFollowing(username)

    override fun getAllUserFavorite(): Flow<List<ItemUser>> =
        userRepository.getAllUserFavorite()

    override suspend fun insertUserFavorite(users: ItemUser) =
        userRepository.insertUserFavorite(users)

    override suspend fun deleteUserFavorite(users: ItemUser) =
        userRepository.deleteUserFavorite(users)

    override fun getFavorite(username: String): Flow<Boolean> =
        userRepository.getFavorite(username)

}