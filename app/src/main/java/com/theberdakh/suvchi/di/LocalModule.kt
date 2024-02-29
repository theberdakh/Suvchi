package com.theberdakh.suvchi.di

import com.theberdakh.suvchi.data.local.pref.LocalPreferences
import org.koin.dsl.module


val localModule = module {
    single {
        LocalPreferences()
    }
}