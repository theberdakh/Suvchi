package com.theberdakh.suvchi.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.theberdakh.suvchi.data.local.demo.AnalyticsDemo
import com.theberdakh.suvchi.databinding.FragmentDayBinding

class DayFragment: Fragment() {
    private var _binding: FragmentDayBinding? = null
    private val binding get() = checkNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       _binding =  FragmentDayBinding.inflate(inflater, container, false)

        binding.iconNavigateBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        val adapter= WaterSpeedAdapter()
        binding.recyclerHours.adapter = adapter
        binding.recyclerHours.addItemDecoration(DividerItemDecoration(binding.recyclerHours.context, LinearLayoutManager.VERTICAL))
        adapter.submitList(AnalyticsDemo.getDemoStatsForWeek()[0].waterSpeedByHours)

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}