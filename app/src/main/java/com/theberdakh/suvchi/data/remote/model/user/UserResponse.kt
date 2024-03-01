package com.theberdakh.suvchi.data.remote.model.user

data class UserResponse(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val middleName: String,
    val phone: String,
    val avatar: String,
    val username: String,
    val latitude: String,
    val longitude: String,
    val passport: String
)
