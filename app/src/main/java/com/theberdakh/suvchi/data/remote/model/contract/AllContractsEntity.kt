package com.theberdakh.suvchi.data.remote.model.contract

data class AllContractsEntity(
    val id: Int,
    val title: String,
    val fileId: Int,
    val createdAt: String,
    val file: String
)
