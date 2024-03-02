package com.theberdakh.suvchi.di

import com.theberdakh.suvchi.domain.AuthRepository
import com.theberdakh.suvchi.domain.UserRepository
import org.koin.dsl.module

val appModule = module {
    single<AuthRepository> {
        AuthRepository(api = get())
    }
    single {
        UserRepository(api = get())
    }
}