package com.theberdakh.suvchi.data.remote.model.statistics

data class DayUsageStatistics(
    val totalTime: Double,
    val date: String,
    val q: String,
    val v: Double,
    val amount: Double
)
