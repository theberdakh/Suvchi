package com.theberdakh.suvchi.data

data class LoginFormState(
    val userName: String = "",
    val userNameError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
)