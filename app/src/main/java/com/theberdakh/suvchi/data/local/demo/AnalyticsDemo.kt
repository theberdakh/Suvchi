package com.theberdakh.suvchi.data.local.demo

import com.patrykandpatrick.vico.core.entry.entriesOf

object AnalyticsDemo {


     fun getDemoWaterSpeedForDay() = listOf<WaterSpeed>(
        WaterSpeed(
            time = "11:00",
            speed = "14"
        ),
        WaterSpeed(
            time = "12:00",
            speed = "14"
        ),
        WaterSpeed(
            time = "13:00",
            speed = "14"
        ),
        WaterSpeed(
            time = "14:00",
            speed = "14"
        ),
        WaterSpeed(
            time = "15:00",
            speed = "14 "
        ),
        WaterSpeed(
            time = "16:00",
            speed = "14"
        ),
        WaterSpeed(
            time = "17:00",
            speed = "14"
        ),
        WaterSpeed(
            time = "18:00",
            speed = "14"
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