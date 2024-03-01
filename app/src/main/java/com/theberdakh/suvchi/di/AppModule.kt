package com.theberdakh.suvchi.di

import com.theberdakh.suvchi.domain.AuthRepository
import org.koin.dsl.module

val appModule = module {
    single<AuthRepository> {
        AuthRepository(api = get())
    }
}