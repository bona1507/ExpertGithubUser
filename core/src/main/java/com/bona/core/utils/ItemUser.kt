package com.bona.core.utils

data class ItemUser(
    val login: String,
    val name: String?,
    val avatarUrl: String?,
    val followers: Int?,
    val following: Int?,
)