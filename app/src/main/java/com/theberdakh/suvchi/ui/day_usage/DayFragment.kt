package com.theberdakh.suvchi.ui.day_usage

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.theberdakh.suvchi.R
import com.theberdakh.suvchi.data.local.demo.AnalyticsDemo
import com.theberdakh.suvchi.data.remote.model.statistics.DayUsageStatistics
import com.theberdakh.suvchi.databinding.FragmentDayBinding
import com.theberdakh.suvchi.ui.report_usage.ReportDayUsageBottomSheet
import com.theberdakh.suvchi.util.convertDateTime
import com.theberdakh.suvchi.util.convertSecondToHourAndMinutes
import com.theberdakh.suvchi.util.enterFullScreen
import com.theberdakh.suvchi.util.exitFullScreen

class DayFragment(val dayUsageStatistics: DayUsageStatistics): Fragment() {
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

        binding.buttonDecline.setOnClickListener{
            declineDayUsageStatistics()
        }

        val adapter= WaterSpeedAdapter()
        binding.recyclerHours.adapter = adapter
        adapter.submitList(AnalyticsDemo.getDemoWaterSpeedForDay())

        return binding.root
    }

    private fun initViews() {
        binding.date.text = convertDateTime(dayUsageStatistics.date, newPattern = "dd-MM-yyyy")
        val (hours, minutes) = convertSecondToHourAndMinutes(dayUsageStatistics.totalTime)
        binding.tvTotalTime.text = getString(R.string.hour_and_minutes, hours, minutes)
        binding.tvQ.text = dayUsageStatistics.q

        val formattedString = String.format("%.1f", dayUsageStatistics.v)
        binding.tvV.text = formattedString

        binding.tvAmount.text = dayUsageStatistics.amount.toString()



    }

    private fun declineDayUsageStatistics() {
        showDeclineDialog(R.layout.dialog_decline)
    }

    private fun showDeclineDialog(@LayoutRes id: Int) {
        val dialog = ReportDayUsageBottomSheet(dayUsageStatistics)
        dialog.show(childFragmentManager, "Tag")

    }

    override fun onDestroyView() {
        _binding = null
        requireActivity().enterFullScreen()
        super.onDestroyView()
    }
}