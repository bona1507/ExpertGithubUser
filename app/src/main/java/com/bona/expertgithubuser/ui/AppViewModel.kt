package com.bona.expertgithubuser.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bona.core.utils.ItemUser
import com.bona.core.utils.ItemUserUseCase
import kotlinx.coroutines.launch

class AppViewModel(private val user: ItemUserUseCase) : ViewModel() {

    fun getUsersByUsername(username: String) = user.getUsername(username).asLiveData()

    fun getUserDetail(username: String) = user.getUserDetail(username).asLiveData()

    fun getUserFollowers(username: String) = user.getUserFollowers(username).asLiveData()

    fun getUserFollowing(username: String) = user.getUserFollowing(username).asLiveData()

    fun insertUserFavorite(users: ItemUser) = viewModelScope.launch {
        user.insertUserFavorite(users)
    }

    fun deleteUserFavorite(users: ItemUser) = viewModelScope.launch {
        user.deleteUserFavorite(users)
    }

    fun getFavoriteIsExists(username: String) = user.getFavorite(username).asLiveData()
}
