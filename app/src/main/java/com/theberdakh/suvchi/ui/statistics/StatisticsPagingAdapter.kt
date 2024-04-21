package com.theberdakh.suvchi.ui.statistics

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.theberdakh.suvchi.data.remote.model.statistics.DayUsageStatistics
import com.theberdakh.suvchi.databinding.ItemListDayCardBinding
import com.theberdakh.suvchi.util.convertDateTime
import com.theberdakh.suvchi.util.convertSecondToHourAndMinutes

class StatisticsPagingAdapter(val listener: StatisticsClickEvent) : PagingDataAdapter<DayUsageStatistics, StatisticsPagingAdapter.StatisticsViewHolder>(StatisticsDiffCallback()){

    interface StatisticsClickEvent{
        fun onClick(dayUsageStatistics: DayUsageStatistics)
    }

    inner class StatisticsViewHolder(private val binding: ItemListDayCardBinding): ViewHolder(binding.root){
        fun bind(dayUsageStatistics: DayUsageStatistics){
            binding.date.text = convertDateTime(dayUsageStatistics.date, newPattern = "dd-MM-yyyy")

            // Example usage:
            val (hours, minutes) = convertSecondToHourAndMinutes(dayUsageStatistics.totalTime)
            binding.tvTotalTime.text = "$hours saat, $minutes minut"
            binding.tvQ.text = dayUsageStatistics.q

            val formattedString = String.format("%.1f", dayUsageStatistics.v)
            binding.tvV.text = formattedString

            binding.tvAmount.text = dayUsageStatistics.amount.toString()

            binding.root.setOnClickListener {
                listener.onClick(dayUsageStatistics)
            }
        }
    }





    class StatisticsDiffCallback: DiffUtil.ItemCallback<DayUsageStatistics>(){
        override fun areItemsTheSame(
            oldItem: DayUsageStatistics,
            newItem: DayUsageStatistics
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: DayUsageStatistics,
            newItem: DayUsageStatistics
        ): Boolean {
           return oldItem.date == newItem.date
        }

    }

    override fun onBindViewHolder(holder: StatisticsViewHolder, position: Int) {
        val statistics = getItem(position)
        if (statistics != null){
            holder.bind(statistics)
        } else {
            Log.i("StatisticsPagingAdapter", "Data in position is null")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatisticsViewHolder {
      return StatisticsViewHolder(ItemListDayCardBinding.inflate(
          LayoutInflater.from(parent.context),
          parent,
          false
      ))
    }
}