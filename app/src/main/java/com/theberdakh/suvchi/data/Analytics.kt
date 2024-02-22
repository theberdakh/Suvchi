package com.theberdakh.suvchi.data

import androidx.recyclerview.widget.DiffUtil
import com.patrykandpatrick.vico.core.entry.FloatEntry

data class Analytics(
    val id: Int,
    val title: String,
    val values: List<FloatEntry>
){
    object Callback: DiffUtil.ItemCallback<Analytics>(){
        override fun areItemsTheSame(oldItem: Analytics, newItem: Analytics): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Analytics, newItem: Analytics): Boolean {
            return oldItem.id == newItem.id
        }

    }
}