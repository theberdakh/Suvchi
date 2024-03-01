package com.theberdakh.suvchi.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theberdakh.suvchi.data.remote.model.LoginRequest
import com.theberdakh.suvchi.data.remote.model.LoginResponse
import com.theberdakh.suvchi.data.remote.model.ResultData
import com.theberdakh.suvchi.domain.AuthRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class LoginViewModel(private val repository: AuthRepository): ViewModel() {
    val responseIsSuccessful = MutableSharedFlow<LoginResponse>()
    val responseIsMessage = MutableSharedFlow<String>()
    val responseIsError = MutableSharedFlow<Throwable>()

    suspend fun login(username: String, password: String){
        repository.login(LoginRequest(username, password)).onEach {
            when(it){
                is ResultData.Success -> {
                    responseIsSuccessful.emit(it.data)
                }
                is ResultData.Message -> {
                    responseIsMessage.emit(it.message)
                }
                is ResultData.Error -> {
                    responseIsError.emit(it.error)
                }
            }
        }.launchIn(viewModelScope)

    }
}