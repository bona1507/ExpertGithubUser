package com.bona.core.data.remote

import com.bona.core.data.local.LocalDataSource
import com.bona.core.data.remote.response.UserItem
import com.bona.core.data.remote.retrofit.ApiResponse
import com.bona.core.utils.DataMapper
import com.bona.core.utils.ItemUser
import com.bona.core.utils.ItemUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ItemUserQuery(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : ItemUserRepository {

    override fun getUsername(username: String): Flow<ItemState<List<ItemUser>>> =
        object :
            NetworkBoundary<List<ItemUser>, List<UserItem>>() {
            override fun loadFromNetwork(data: List<UserItem>): Flow<List<ItemUser>> {
                return DataMapper.mapResponsesToDomain(data)
            }

            override suspend fun createCall(): Flow<ApiResponse<List<UserItem>>> =
                remoteDataSource.getUsersByUsername(username)

        }.asFlow()

    override fun getUserDetail(username: String): Flow<ItemState<ItemUser>> =
        object : NetworkBoundary<ItemUser, UserItem>() {
            override fun loadFromNetwork(data: UserItem): Flow<ItemUser> {
                return DataMapper.mapResponsesToDomain(data)
            }

            override suspend fun createCall(): Flow<ApiResponse<UserItem>> =
                remoteDataSource.getUserDetail(username)

        }.asFlow()


    override fun getUserFollowers(username: String): Flow<ItemState<List<ItemUser>>> =
        object :
            NetworkBoundary<List<ItemUser>, List<UserItem>>() {
            override fun loadFromNetwork(data: List<UserItem>): Flow<List<ItemUser>> {
                return DataMapper.mapResponsesToDomain(data)
            }

            override suspend fun createCall(): Flow<ApiResponse<List<UserItem>>> =
                remoteDataSource.getUserFollowers(username)

        }.asFlow()

    override fun getUserFollowing(username: String): Flow<ItemState<List<ItemUser>>> =
        object :
            NetworkBoundary<List<ItemUser>, List<UserItem>>() {
            override fun loadFromNetwork(data: List<UserItem>): Flow<List<ItemUser>> {
                return DataMapper.mapResponsesToDomain(data)
            }

            override suspend fun createCall(): Flow<ApiResponse<List<UserItem>>> =
                remoteDataSource.getUserFollowing(username)

        }.asFlow()

    override fun getAllUserFavorite(): Flow<List<ItemUser>> {
        return localDataSource.getAllUserFavorite().map { userEntities ->
            userEntities.let {
                DataMapper.mapEntitiesToDomain(it)
            }
        }
    }

    override suspend fun insertUserFavorite(users: ItemUser) {
        localDataSource.insertUserFavorite(DataMapper.mapDomainToEntity(users))
    }

    override suspend fun deleteUserFavorite(users: ItemUser) {
        localDataSource.deleteUserFavorite(DataMapper.mapDomainToEntity(users))
    }

    override fun getFavorite(username: String): Flow<Boolean> =
        localDataSource.getFavorite(username)

}