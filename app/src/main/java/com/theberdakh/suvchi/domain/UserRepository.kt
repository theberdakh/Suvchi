package com.theberdakh.suvchi.domain

import com.theberdakh.suvchi.data.remote.UserApi
import com.theberdakh.suvchi.data.remote.model.ResultData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UserRepository(private val api: UserApi) {

    suspend fun getUserProfile() = flow {
        val response = api.getUserProfile()
        if (response.isSuccessful){
            emit(ResultData.Success(response.body()!!))
        } else {
            emit(ResultData.Message(response.code().toString()))
        }
    }.catch {
        emit(ResultData.Error(it))
    }.flowOn(Dispatchers.IO)
}