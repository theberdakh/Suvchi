package com.theberdakh.suvchi.data.local.demo

import androidx.recyclerview.widget.DiffUtil

data class DailyUsage(
    val id: Int,
    val date: Int,
    val isConfirmed: Boolean,
    val startTimeWaterConsumption: Int,
    val endTimeWaterConsumption: Int,
    val totalTimeWaterConsumption: Int,
    val totalValueWaterConsumption: Int,
    val waterSpeedByHours: List<WaterSpeed>
){
    object Callback: DiffUtil.ItemCallback<DailyUsage>(){
        override fun areItemsTheSame(oldItem: DailyUsage, newItem: DailyUsage): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: DailyUsage, newItem: DailyUsage): Boolean {
           return oldItem.id == newItem.id
        }

    }
}
