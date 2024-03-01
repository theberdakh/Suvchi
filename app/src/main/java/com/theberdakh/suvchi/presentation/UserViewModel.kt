package com.theberdakh.suvchi.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theberdakh.suvchi.data.remote.model.ResultData
import com.theberdakh.suvchi.data.remote.model.user.UserResponse
import com.theberdakh.suvchi.domain.UserRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class UserViewModel(private val repository: UserRepository): ViewModel() {
    val userProfileResponseSuccessful = MutableSharedFlow<UserResponse>()
    val userProfileResponseMessage = MutableSharedFlow<String>()
    val userProfileResponseError = MutableSharedFlow<Throwable>()


    suspend fun getUserProfile(){
        repository.getUserProfile().onEach {
            when(it){
                is ResultData.Success -> userProfileResponseSuccessful.emit(it.data)
                is ResultData.Message -> userProfileResponseMessage.emit(it.message)
                is ResultData.Error -> userProfileResponseError.emit(it.error)
            }
        }.launchIn(viewModelScope)
    }

}