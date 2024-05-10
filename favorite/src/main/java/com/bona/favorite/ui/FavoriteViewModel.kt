package com.bona.favorite.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bona.core.utils.ItemUserUseCase

class FavoriteViewModel(private val userUseCase: ItemUserUseCase) : ViewModel() {
    fun getAllUserFavorite() = userUseCase.getAllUserFavorite().asLiveData()
}