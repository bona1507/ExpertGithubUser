package com.bona.core.data.remote.retrofit

import com.bona.core.data.remote.response.GithubResponse
import com.bona.core.data.remote.response.UserItem
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    suspend fun getUsername(
        @Query("q") username: String,
    ): GithubResponse

    @GET("users/{username}")
    suspend fun getDetailUser(
        @Path("username") username: String,
    ): UserItem

    @GET("users/{username}/followers")
    suspend fun getFollowers(
        @Path("username") username: String,
    ): List<UserItem>

    @GET("users/{username}/following")
    suspend fun getFollowing(
        @Path("username") username: String,
    ): List<UserItem>
}