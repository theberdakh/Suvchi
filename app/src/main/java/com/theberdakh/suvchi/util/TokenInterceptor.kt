package com.theberdakh.suvchi.util

import android.util.Log
import com.theberdakh.suvchi.data.local.pref.LocalPreferences
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor : Interceptor {



    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer ${LocalPreferences().accessToken}")
            .build()

        val response = chain.proceed(request)
        if (response.code == 401) {
            Log.d("intercept", "refresh token is used")
            response.close()
            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer ${LocalPreferences().refreshToken}")
                .build()
            return chain.proceed(newRequest)
        }

        Log.d("intercept", "access token is used")
        return response
    }
}