package com.theberdakh.suvchi.data.remote

import com.theberdakh.suvchi.data.remote.model.user.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface UserApi {

    @GET("/user/profile")
    suspend fun getUserProfile(): Response<UserResponse>
}