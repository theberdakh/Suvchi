package com.theberdakh.suvchi.ui.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.patrykandpatrick.vico.core.entry.entriesOf
import com.patrykandpatrick.vico.core.entry.entryModelOf
import com.theberdakh.suvchi.data.local.demo.Analytics
import com.theberdakh.suvchi.databinding.ItemListDayChartBinding

class DashboardAdapter(private val onDateClick: (View) -> Unit): ListAdapter<Analytics, DashboardAdapter.DashboardViewHolder>(
    Analytics.Callback)
{
    inner class DashboardViewHolder(private val binding: ItemListDayChartBinding): ViewHolder(binding.root){
        fun bind(analytics: Analytics){

            // entriesOf(12f, 16f, 4f, 12f)
            val twoLine = entryModelOf(entriesOf(4f, 12f, 8f, 16f, 13, 14, 14, 14, 14, 21, 33, 40))
            binding.title.text = analytics.title
            binding.chartView.setModel(twoLine)





        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
       return DashboardViewHolder(
            ItemListDayChartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) = holder.bind(getItem(position))
}