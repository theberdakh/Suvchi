package com.theberdakh.suvchi.data.remote.model.auth

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String
)
