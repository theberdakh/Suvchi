package com.theberdakh.suvchi.di

import com.theberdakh.suvchi.presentation.LoginViewModel
import com.theberdakh.suvchi.presentation.UserViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel<LoginViewModel> {
        LoginViewModel(repository = get())
    }
    viewModel{
        UserViewModel(repository = get())
    }

}