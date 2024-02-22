package com.theberdakh.suvchi.data

import com.patrykandpatrick.vico.core.entry.entriesOf

object AnalyticsDemo {

    fun getDemoStatsForWeek() = listOf<DailyUsage>(
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
        ),
        DailyUsage(
            id = 1,
            date = 24,
            isConfirmed = false,
            startTimeWaterConsumption = 1,
            endTimeWaterConsumption = 2,
            totalTimeWaterConsumption = 3,
            totalValueWaterConsumption = 1,
            waterSpeedByHours = getDemoWaterSpeedForDay()
        ),
        DailyUsage(
            id = 1,
            date = 25,
            isConfirmed = false,
            startTimeWaterConsumption = 1,
            endTimeWaterConsumption = 2,
            totalTimeWaterConsumption = 3,
            totalValueWaterConsumption = 1,
            waterSpeedByHours = getDemoWaterSpeedForDay()
        ),
        DailyUsage(
            id = 1,
            date = 26,
            isConfirmed = false,
            startTimeWaterConsumption = 1,
            endTimeWaterConsumption = 2,
            totalTimeWaterConsumption = 3,
            totalValueWaterConsumption = 1,
            waterSpeedByHours = getDemoWaterSpeedForDay()
        ),
        DailyUsage(
            id = 1,
            date = 27,
            isConfirmed = true,
            startTimeWaterConsumption = 1,
            endTimeWaterConsumption = 2,
            totalTimeWaterConsumption = 3,
            totalValueWaterConsumption = 1,
            waterSpeedByHours = getDemoWaterSpeedForDay()
        ),
        DailyUsage(
            id = 1,
            date = 28,
            isConfirmed = false,
            startTimeWaterConsumption = 1,
            endTimeWaterConsumption = 2,
            totalTimeWaterConsumption = 3,
            totalValueWaterConsumption = 1,
            waterSpeedByHours = getDemoWaterSpeedForDay()
        ),
    )

    private fun getDemoWaterSpeedForDay() = listOf<WaterSpeed>(
        WaterSpeed(
            time = "11:00",
            speed = "14 km/soat"
        ),
        WaterSpeed(
            time = "11:00",
            speed = "14 km/soat"
        ),
        WaterSpeed(
            time = "11:00",
            speed = "14 km/soat"
        ),
        WaterSpeed(
            time = "11:00",
            speed = "14 km/soat"
        ),
        WaterSpeed(
            time = "11:00",
            speed = "14 km/soat"
        ),
        WaterSpeed(
            time = "11:00",
            speed = "14 km/soat"
        ),
        WaterSpeed(
            time = "11:00",
            speed = "14 km/soat"
        ),
        WaterSpeed(
            time = "11:00",
            speed = "14 km/soat"
        ),
    )

    fun getDemoResults() = listOf<Analytics>(
        Analytics(
            id = 1,
            title = "Suv sarfi",
            values = entriesOf(12, 13, 14, 15)
        ),
        Analytics(
            id = 1,
            title = "Suv tezligi",
            values = entriesOf(12, 13, 14, 15)
        ),
        Analytics(
            id = 1,
            title = "Suvdan foydalanigan vaqtlar",
            values = entriesOf(12, 13, 14, 15)
        ),
        Analytics(
            id = 1,
            title = "Suv olish davri",
            values = entriesOf(12, 13, 14, 15)
        ),
        Analytics(
            id = 1,
            title = "Foydalanilgan suv ko'lami",
            values = entriesOf(12, 13, 14, 15)
        )
    )
}