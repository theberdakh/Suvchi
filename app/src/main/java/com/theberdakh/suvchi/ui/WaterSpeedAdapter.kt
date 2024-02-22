package com.theberdakh.suvchi.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.theberdakh.suvchi.data.WaterSpeed
import com.theberdakh.suvchi.databinding.ItemListWaterSpeedBinding

class WaterSpeedAdapter: ListAdapter<WaterSpeed, WaterSpeedAdapter.WaterSpeedViewHolder>(WaterSpeed.Callback) {

    inner class WaterSpeedViewHolder(private val binding: ItemListWaterSpeedBinding): ViewHolder(binding.root){

        fun bind(){
            binding.hour.text = getItem(adapterPosition).time
            binding.speed.text = getItem(adapterPosition).speed
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WaterSpeedViewHolder {
        return WaterSpeedViewHolder(ItemListWaterSpeedBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: WaterSpeedViewHolder, position: Int) = holder.bind()
}
