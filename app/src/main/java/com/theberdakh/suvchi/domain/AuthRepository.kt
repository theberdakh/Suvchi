package com.theberdakh.suvchi.domain

import com.theberdakh.suvchi.data.remote.LoginApi
import com.theberdakh.suvchi.data.remote.model.auth.LoginRequest
import com.theberdakh.suvchi.data.remote.model.ResultData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AuthRepository(private val api: LoginApi) {

    suspend fun login(loginRequest: LoginRequest) = flow {
        val response = api.login(loginRequest)
        if (response.isSuccessful){
            emit(ResultData.Success(response.body()!!))
        }
        else {
            emit(ResultData.Message(response.code().toString()))
        }
    }.catch {
        emit(ResultData.Error(it))
    }.flowOn(Dispatchers.IO)
}