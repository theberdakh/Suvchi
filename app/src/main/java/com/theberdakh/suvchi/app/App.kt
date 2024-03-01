package com.theberdakh.suvchi.app

import android.app.Application
import com.theberdakh.suvchi.di.appModule
import com.theberdakh.suvchi.di.localModule
import com.theberdakh.suvchi.di.networkModule
import com.theberdakh.suvchi.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {

    companion object{
        lateinit var instance: Application
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        startKoin {
            androidContext(this@App)
            modules(appModule,localModule, networkModule, viewModelModule)
        }

    }

}