package com.theberdakh.suvchi.domain

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import com.theberdakh.suvchi.data.remote.UserApi
import com.theberdakh.suvchi.data.remote.model.ResultData
import com.theberdakh.suvchi.data.remote.model.contract.ContractStatusBody
import com.theberdakh.suvchi.data.remote.utils.convertToMessage
import com.theberdakh.suvchi.ui.contracts.ContractsPagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn

class UserRepository( val api: UserApi) {
    suspend fun getUserProfile() = flow {
        val response = api.getUserProfile()
        if (response.isSuccessful){
            emit(ResultData.Success(response.body()!!))
        } else {
            emit(ResultData.Message(response.code().convertToMessage()))
        }
    }.catch {
        emit(ResultData.Error(it))
    }.flowOn(Dispatchers.IO)

    suspend fun getUserContracts() = flow {
        val response = api.getUserContracts(1, 1,)
        if (response.isSuccessful){

            ContractsPagingSource(api)
            emit(ResultData.Success(checkNotNull(response.body())))
        } else {
            emit(ResultData.Message(response.code().convertToMessage()))
        }
    }.catch {
        emit(ResultData.Error(it))
    }.flowOn(Dispatchers.IO)

    suspend fun setUserContractStatus(body: ContractStatusBody) = flow {
        val response = api.setUserContractStatus(body)
        if (response.isSuccessful){
            emit(ResultData.Success(checkNotNull(response.body())))
        } else {
            emit(ResultData.Message(response.code().convertToMessage()))
        }
    }.catch {
        emit(ResultData.Error(it))
    }.flowOn(Dispatchers.IO)
}