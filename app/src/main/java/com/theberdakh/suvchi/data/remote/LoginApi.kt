package com.theberdakh.suvchi.data.remote

import com.theberdakh.suvchi.data.remote.model.LoginRequest
import com.theberdakh.suvchi.data.remote.model.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {

    @POST("auth/sign-in")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

}