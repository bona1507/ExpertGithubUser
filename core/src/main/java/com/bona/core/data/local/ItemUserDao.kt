package com.bona.core.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemUserDao {

    @Query("SELECT * FROM users")
    fun getAllUserFavorite(): Flow<List<ItemUserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserFavorite(user: ItemUserEntity)

    @Delete
    suspend fun deleteUserFavorite(user: ItemUserEntity)

    @Query("SELECT EXISTS(SELECT * FROM users WHERE username = :username)")
    fun getFavorite(username: String): Flow<Boolean>
}