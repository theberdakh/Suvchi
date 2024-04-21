package com.theberdakh.suvchi.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.theberdakh.suvchi.data.remote.model.ResultData
import com.theberdakh.suvchi.data.remote.model.contract.AllContractsResponse
import com.theberdakh.suvchi.data.remote.model.contract.ContractStatusBody
import com.theberdakh.suvchi.data.remote.model.contract.ContractStatusResponse
import com.theberdakh.suvchi.data.remote.model.statistics.DayUsageStatistics
import com.theberdakh.suvchi.data.remote.model.user.UserResponse
import com.theberdakh.suvchi.domain.UserRepository
import com.theberdakh.suvchi.ui.contracts.ContractsPagingSource
import com.theberdakh.suvchi.ui.statistics.StatisticsPagingSource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

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


    val contracts = Pager(
        PagingConfig(pageSize = 1)
    ) {
        ContractsPagingSource(repository.api)
    }.flow.cachedIn(viewModelScope)




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

    private val _periodStatisticsFlow = MutableStateFlow<PagingData<DayUsageStatistics>?>(null)
    val periodStatistics: StateFlow<PagingData<DayUsageStatistics>?> = _periodStatisticsFlow.asStateFlow()
    fun getPeriodStatistics(fromDate: String, toDate: String) {
        val newPager = Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { StatisticsPagingSource(repository.api, fromDate, toDate) }
        )
        viewModelScope.launch {
            newPager.flow.cachedIn(viewModelScope).collectLatest {
                _periodStatisticsFlow.value = it
            }
        }
    }
}


