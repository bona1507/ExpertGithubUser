package com.bona.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ItemUserEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ItemUserDatabase : RoomDatabase() {

    abstract fun itemUserDao(): ItemUserDao
}