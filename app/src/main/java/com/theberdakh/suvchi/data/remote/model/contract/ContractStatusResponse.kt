package com.theberdakh.suvchi.data.remote.model.contract


/* "userId": 1,
    "contractId": 3,
    "status": "ACEPTED",
    "id": 3,
    "createdAt": "2024-03-12T15:03:01.679Z",
    "updatedAt": "2024-03-12T15:03:01.679Z"*/
data class ContractStatusResponse(
    val userId : Int,
    val contractId: Int,
    val status: String,
    val id: Int,
    val createdAt: String,
    val updatedAt: String
)
