package com.theberdakh.suvchi.data.remote.model.contract


data class AllContractsResponse(
    val data: List<AllContractsEntity>,
    val count: Int
)
