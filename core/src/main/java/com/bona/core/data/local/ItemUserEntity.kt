package com.bona.core.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class ItemUserEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "username")
    var login: String,

    @ColumnInfo(name = "name")
    var name: String?,

    @ColumnInfo(name = "avatar_url")
    var avatarUrl: String?,

    @ColumnInfo(name = "followers")
    var followers: Int?,

    @ColumnInfo(name = "following")
    var following: Int?,
)
