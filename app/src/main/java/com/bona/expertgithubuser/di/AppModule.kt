package com.bona.expertgithubuser.di

import com.bona.core.utils.ItemUserInteractor
import com.bona.core.utils.ItemUserUseCase
import com.bona.expertgithubuser.ui.AppViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<ItemUserUseCase> { ItemUserInteractor(get()) }
}

val viewModelModule = module {
    viewModel {AppViewModel(get())}
}