package com.theberdakh.suvchi.data.remote

import com.theberdakh.suvchi.data.remote.model.contract.AllContractsResponse
import com.theberdakh.suvchi.data.remote.model.contract.ContractStatusBody
import com.theberdakh.suvchi.data.remote.model.contract.ContractStatusResponse
import com.theberdakh.suvchi.data.remote.model.user.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserApi {

    @GET("/user/profile")
    suspend fun getUserProfile(): Response<UserResponse>

    @GET("/user/contracts")
    suspend fun getUserContracts(
        @Query("limit") limit: Int,
        @Query("skip") skip: Int,
        @Query("sort[id]") id: String = "DESC"
    ): Response<AllContractsResponse>


    @POST("/user/contracts/status")
    suspend fun setUserContractStatus(@Body contractStatusBody: ContractStatusBody): Response<ContractStatusResponse>

}