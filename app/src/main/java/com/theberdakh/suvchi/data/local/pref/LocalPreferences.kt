package com.theberdakh.suvchi.data.local.pref

import android.content.Context
import android.provider.SyncStateContract
import com.theberdakh.suvchi.app.App
import com.theberdakh.suvchi.util.BooleanPreference
import com.theberdakh.suvchi.util.IntPreference
import com.theberdakh.suvchi.util.StringPreference

class LocalPreferences {

    companion object{
        private val pref = App.instance.getSharedPreferences("pref", Context.MODE_PRIVATE)
    }

    var isLoggedIn by BooleanPreference(pref, false)
    var userName by StringPreference(pref)
    var password by StringPreference(pref)
    var accessToken by StringPreference(pref)
    var refreshToken by StringPreference(pref)

}