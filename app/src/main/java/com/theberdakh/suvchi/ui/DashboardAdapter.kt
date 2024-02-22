package com.theberdakh.suvchi.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.patrykandpatrick.vico.core.entry.entriesOf
import com.patrykandpatrick.vico.core.entry.entryModelOf
import com.theberdakh.suvchi.R
import com.theberdakh.suvchi.data.Analytics
import com.theberdakh.suvchi.databinding.ItemListDayChartBinding

class DashboardAdapter(private val onDateClick: (View) -> Unit): ListAdapter<Analytics, DashboardAdapter.DashboardViewHolder>(Analytics.Callback)
{
    inner class DashboardViewHolder(private val binding: ItemListDayChartBinding): ViewHolder(binding.root){
        fun bind(analytics: Analytics){
            val twoLine = entryModelOf(entriesOf(4f, 12f, 8f, 16f), entriesOf(12f, 16f, 4f, 12f))
            binding.title.text = analytics.title
            binding.chartView.setModel(twoLine)
            binding.chartDate.setOnClickListener {
                onDateClick.invoke(binding.chartDate)
            }


        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
       return DashboardViewHolder(
            ItemListDayChartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) = holder.bind(getItem(position))
}