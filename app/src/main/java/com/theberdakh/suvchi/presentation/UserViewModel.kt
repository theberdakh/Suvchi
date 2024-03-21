package com.theberdakh.suvchi.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.theberdakh.suvchi.data.remote.model.ResultData
import com.theberdakh.suvchi.data.remote.model.contract.AllContractsEntity
import com.theberdakh.suvchi.data.remote.model.contract.AllContractsResponse
import com.theberdakh.suvchi.data.remote.model.contract.ContractStatusBody
import com.theberdakh.suvchi.data.remote.model.contract.ContractStatusResponse
import com.theberdakh.suvchi.data.remote.model.user.UserResponse
import com.theberdakh.suvchi.domain.UserRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class UserViewModel(private val repository: UserRepository) : ViewModel() {
    val userProfileResponseSuccessful = MutableSharedFlow<UserResponse>()
    val userProfileResponseMessage = MutableSharedFlow<String>()
    val userProfileResponseError = MutableSharedFlow<Throwable>()

    val allContractsResponseSuccess = MutableSharedFlow<AllContractsResponse>()
    val allContractsResponseMessage = MutableSharedFlow<String>()
    val allContractsResponseError = MutableSharedFlow<Throwable>()

    val statusContractSuccess = MutableSharedFlow<ContractStatusResponse>()
    val statusContractMessage = MutableSharedFlow<String>()
    val statusContractError = MutableSharedFlow<Throwable>()

    val dataFlow = MutableSharedFlow<PagingData<AllContractsEntity>>()



    suspend fun getUserProfile() {
        repository.getUserProfile().onEach {
            when (it) {
                is ResultData.Success -> userProfileResponseSuccessful.emit(it.data)
                is ResultData.Message -> userProfileResponseMessage.emit(it.message)
                is ResultData.Error -> userProfileResponseError.emit(it.error)
            }
        }.launchIn(viewModelScope)
    }

    suspend fun getUserContracts() {

        repository.getUserContracts().onEach {
            when (it) {
                is ResultData.Success -> allContractsResponseSuccess.emit(it.data)
                is ResultData.Message -> allContractsResponseMessage.emit(it.message)
                is ResultData.Error -> allContractsResponseError.emit(it.error)
            }
        }.launchIn(viewModelScope)
    }


    suspend fun setUserContractPositive(id: Int) {
        val userContractStatusBody = ContractStatusBody(id, "ACEPTED")
        repository.setUserContractStatus(userContractStatusBody).onEach {
            when (it) {
                is ResultData.Success -> statusContractSuccess.emit(it.data)
                is ResultData.Message -> statusContractMessage.emit(it.message)
                is ResultData.Error -> statusContractError.emit(it.error)
            }
        }.launchIn(viewModelScope)
    }

    suspend fun setUserContractNegative(id: Int){
        val userContractStatusBody = ContractStatusBody(id, "DECLINE")
        repository.setUserContractStatus(userContractStatusBody).onEach {
            when (it) {
                is ResultData.Success -> statusContractSuccess.emit(it.data)
                is ResultData.Message -> statusContractMessage.emit(it.message)
                is ResultData.Error -> statusContractError.emit(it.error)
            }
        }.launchIn(viewModelScope)
    }


}