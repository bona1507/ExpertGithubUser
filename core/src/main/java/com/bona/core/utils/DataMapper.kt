package com.bona.core.utils

import com.bona.core.data.local.ItemUserEntity
import com.bona.core.data.remote.response.UserItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

object DataMapper {

    fun mapDomainToEntity(input: ItemUser) = ItemUserEntity(
        login = input.login,
        name = input.name,
        avatarUrl = input.avatarUrl,
        followers = input.followers,
        following = input.following
    )

    fun mapResponsesToDomain(input: List<UserItem>): Flow<List<ItemUser>> = flowOf(
        input.map {
            ItemUser(
                login = it.login,
                name = it.name,
                avatarUrl = it.avatarUrl,
                followers = it.followers,
                following = it.following
            )
        }
    )

    fun mapResponsesToDomain(input: UserItem): Flow<ItemUser> = flowOf(
        ItemUser(
            login = input.login,
            name = input.name,
            avatarUrl = input.avatarUrl,
            followers = input.followers,
            following = input.following
        )
    )

    fun mapEntitiesToDomain(input: List<ItemUserEntity>): List<ItemUser> =
        input.map {
            ItemUser(
                login = it.login,
                name = it.name,
                avatarUrl = it.avatarUrl,
                followers = it.followers,
                following = it.following
            )
        }
}
