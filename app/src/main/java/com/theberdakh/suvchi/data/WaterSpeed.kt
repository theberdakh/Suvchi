package com.theberdakh.suvchi.data

import androidx.recyclerview.widget.DiffUtil

data class WaterSpeed(
    val time: String,
    val speed: String
){
    object Callback: DiffUtil.ItemCallback<WaterSpeed>(){
        override fun areItemsTheSame(oldItem: WaterSpeed, newItem: WaterSpeed): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: WaterSpeed, newItem: WaterSpeed): Boolean {
           return oldItem.time == newItem.time
        }
    }
}
