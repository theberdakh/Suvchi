package com.theberdakh.suvchi.ui.report_usage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.theberdakh.suvchi.R
import com.theberdakh.suvchi.data.remote.model.statistics.DayUsageStatistics
import com.theberdakh.suvchi.databinding.DialogDeclineBinding
import com.theberdakh.suvchi.util.convertDateTime
import com.theberdakh.suvchi.util.convertSecondToHourAndMinutes

class ReportDayUsageBottomSheet(private val dayUsageStatistics: DayUsageStatistics): BottomSheetDialogFragment(){
    private var _binding: DialogDeclineBinding? = null
    private val binding get() = checkNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = DialogDeclineBinding.inflate(inflater, container, false)

        initViews()
        binding.layoutWaterUsage.isVisible = true

        binding.buttonCancel.setOnClickListener {
            dismissNow()
        }




        return binding.root
    }

    private fun initViews() {
        binding.date.text = convertDateTime(dayUsageStatistics.date, newPattern = "dd-MM-yyyy")
        val (hours, minutes) = convertSecondToHourAndMinutes(dayUsageStatistics.totalTime)
        binding.tvTotalTime.text = getString(R.string.hour_and_minutes, hours, minutes)
        binding.tvUsage.text = dayUsageStatistics.q

        val formattedString = String.format("%.1f", dayUsageStatistics.v)
        binding.tvSpeed.text = formattedString

        binding.tvAmount.text = dayUsageStatistics.amount.toString()

    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}