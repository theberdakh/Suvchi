package com.theberdakh.suvchi.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.theberdakh.suvchi.data.local.demo.DailyUsage
import com.theberdakh.suvchi.databinding.ItemListDayCardBinding

class DayAdapter(
    val onAcceptClick: (DailyUsage) -> Unit,
    val onDeclineClick: (DailyUsage) -> Unit,
    val onClick: (DailyUsage) -> Unit
) : ListAdapter<DailyUsage, DayAdapter.DayViewHolder>(DailyUsage.Callback) {

    init {
        setHasStableIds(true)
    }

    inner class DayViewHolder(private val binding: ItemListDayCardBinding) :
        ViewHolder(binding.root) {
        fun bind(dailyUsage: DailyUsage) {
            binding.root.setOnClickListener {
                onClick.invoke(dailyUsage)
            }

            if (dailyUsage.isConfirmed) {
                //hide accept/decline if already accepted
                binding.layoutAcceptDecline.isVisible = false
                binding.buttonAccepted.isVisible = true
            }

            binding.buttonAccept.setOnClickListener {
                onAcceptClick.invoke(dailyUsage)
                binding.layoutAcceptDecline.isVisible = false
                binding.buttonAccepted.isVisible = true
            }

            binding.buttonDecline.setOnClickListener {
                onDeclineClick.invoke(dailyUsage)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        return DayViewHolder(
            ItemListDayCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) =
        holder.bind(getItem(position))
}

