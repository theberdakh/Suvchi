package com.theberdakh.suvchi.domain

import android.util.Log
import com.google.gson.JsonObject
import com.patrykandpatrick.vico.core.extension.getFieldValue
import com.theberdakh.suvchi.data.remote.LoginApi
import com.theberdakh.suvchi.data.remote.model.LoginRequest
import com.theberdakh.suvchi.data.remote.model.LoginResponse
import com.theberdakh.suvchi.data.remote.model.ResultData
import com.theberdakh.suvchi.util.showToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.json.JSONObject

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