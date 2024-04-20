package com.theberdakh.suvchi.ui.day_usage

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.theberdakh.suvchi.data.local.demo.AnalyticsDemo
import com.theberdakh.suvchi.databinding.FragmentDayBinding
import com.theberdakh.suvchi.util.enterFullScreen
import com.theberdakh.suvchi.util.exitFullScreen

class DayFragment: Fragment() {
    private var _binding: FragmentDayBinding? = null
    private val binding get() = checkNotNull(_binding)


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requireActivity().exitFullScreen()
       _binding =  FragmentDayBinding.inflate(inflater, container, false)

        initViews()
        binding.toolbarNavigationBack.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }

        val adapter= WaterSpeedAdapter()
        binding.recyclerHours.adapter = adapter
        binding.recyclerHours.addItemDecoration(DividerItemDecoration(binding.recyclerHours.context, LinearLayoutManager.VERTICAL))
        adapter.submitList(AnalyticsDemo.getDemoStatsForWeek()[0].waterSpeedByHours)

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initViews() {

    }

    override fun onDestroyView() {
        _binding = null
        requireActivity().enterFullScreen()
        super.onDestroyView()
    }
}