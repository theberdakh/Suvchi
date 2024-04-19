package com.theberdakh.suvchi.data.local.demo

import com.patrykandpatrick.vico.core.entry.entriesOf

object AnalyticsDemo {

    fun getDemoStatsForWeek() = listOf(
        DailyUsage(
            id = 1,
            date = 22,
            isConfirmed = false,
            startTimeWaterConsumption = 1,
            endTimeWaterConsumption = 2,
            totalTimeWaterConsumption = 3,
            totalValueWaterConsumption = 1,
            waterSpeedByHours = getDemoWaterSpeedForDay()
        ),
        DailyUsage(
            id = 1,
            date = 22,
            isConfirmed = false,
            startTimeWaterConsumption = 1,
            endTimeWaterConsumption = 2,
            totalTimeWaterConsumption = 3,
            totalValueWaterConsumption = 1,
            waterSpeedByHours = getDemoWaterSpeedForDay()
        )
    )

    private fun getDemoWaterSpeedForDay() = listOf<WaterSpeed>(
        WaterSpeed(
            time = "11:00",
            speed = "14 km/soat"
        ),
        WaterSpeed(
            time = "12:00",
            speed = "14 km/soat"
        ),
        WaterSpeed(
            time = "13:00",
            speed = "14 km/soat"
        ),
        WaterSpeed(
            time = "14:00",
            speed = "14 km/soat"
        ),
        WaterSpeed(
            time = "15:00",
            speed = "14 km/soat"
        ),
        WaterSpeed(
            time = "16:00",
            speed = "14 km/soat"
        ),
        WaterSpeed(
            time = "17:00",
            speed = "14 km/soat"
        ),
        WaterSpeed(
            time = "18:00",
            speed = "14 km/soat"
        ),
    )

    fun getDemoResults() = listOf(
        Analytics(
            id = 1,
            title = "Suw sarpı",
            values = entriesOf(12, 13, 14, 15)
        ),
        Analytics(
            id = 1,
            title = "Suw tezligi",
            values = entriesOf(12, 13, 14, 15)
        )
    )
}