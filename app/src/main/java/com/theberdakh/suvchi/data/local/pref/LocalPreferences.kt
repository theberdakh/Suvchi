package com.theberdakh.suvchi.data.local.pref

import android.content.Context
import com.theberdakh.suvchi.app.App
import com.theberdakh.suvchi.data.remote.model.auth.LoginResponse
import com.theberdakh.suvchi.data.remote.model.user.UserResponse
import com.theberdakh.suvchi.util.BooleanPreference
import com.theberdakh.suvchi.util.IntPreference
import com.theberdakh.suvchi.util.StringPreference

class LocalPreferences {

    companion object {
        private val pref = App.instance.getSharedPreferences("pref", Context.MODE_PRIVATE)
        const val EMPTY_STRING = ""
    }

    /*Auth*/
    private var isLoggedIn by BooleanPreference(pref, false)
    private var accessToken by StringPreference(pref)
    private var refreshToken by StringPreference(pref)

    fun saveUserToken(loginResponse: LoginResponse) {
        accessToken = loginResponse.accessToken
        refreshToken = loginResponse.refreshToken
        isLoggedIn = true
    }

    fun isUserLoggedIn() = isLoggedIn

    fun getUserAccessToken(): String = accessToken
    fun getUserRefreshToken(): String = refreshToken

    fun clearUserData() {
        accessToken = EMPTY_STRING
        refreshToken = EMPTY_STRING
        firstName = EMPTY_STRING
        middleName = EMPTY_STRING
        lastName = EMPTY_STRING
        phone = EMPTY_STRING
        avatar = EMPTY_STRING
        username = EMPTY_STRING
        latitude = EMPTY_STRING
        longitude = EMPTY_STRING
        passport = EMPTY_STRING
        isLoggedIn = false
    }

    fun getUserData(): UserResponse {
        return UserResponse(
            id = userId,
            firstName = firstName,
            lastName = lastName,
            middleName = middleName,
            phone = phone,
            avatar = avatar,
            username = username,
            latitude = latitude,
            longitude = longitude,
            passport = passport
        )
    }

    fun saveUserResponse(userResponse: UserResponse) {
        userId = userResponse.id
        firstName = userResponse.firstName
        middleName = userResponse.middleName
        lastName = userResponse.lastName
        phone = userResponse.phone
        avatar = userResponse.avatar
        username = userResponse.username
        latitude = userResponse.latitude
        longitude = userResponse.longitude
        passport = userResponse.passport
    }

    /*User info*/
    private var userId: Int by IntPreference(pref)
    private var firstName by StringPreference(pref)
    private var lastName by StringPreference(pref)
    private var middleName by StringPreference(pref)
    private var phone by StringPreference(pref)
    private var avatar by StringPreference(pref)
    private var username by StringPreference(pref)
    private var latitude by StringPreference(pref)
    private var longitude by StringPreference(pref)
    private var passport by StringPreference(pref)

}