package com.theberdakh.suvchi.ui.statistics

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import com.theberdakh.suvchi.R
import com.theberdakh.suvchi.data.remote.model.statistics.DayUsageStatistics
import com.theberdakh.suvchi.databinding.FragmentStatisticsBinding
import com.theberdakh.suvchi.presentation.UserViewModel
import com.theberdakh.suvchi.ui.day_usage.DayFragment
import com.theberdakh.suvchi.util.addFragmentToBackStack
import com.theberdakh.suvchi.util.enterFullScreen
import com.theberdakh.suvchi.util.hide
import com.theberdakh.suvchi.util.show
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class StatisticsFragment : Fragment(), StatisticsPagingAdapter.StatisticsClickEvent {
    private var _binding: FragmentStatisticsBinding? = null
    private val binding get() = checkNotNull(_binding)
    private val adapter = StatisticsPagingAdapter(this)
    private val userViewModel by viewModel<UserViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatisticsBinding.inflate(inflater, container, false)

        initViews()
        initListeners()
        initObservers()

        return binding.root
    }

    private fun initObservers() {
        lifecycleScope.launch {
            val (fromDate, toDate) = getLastDayRange(10)
            binding.buttonDateRange.text = "2024-04-19 - 2024-04-21"
            userViewModel.getPeriodStatistics("2024-04-19", "2024-04-21")
            userViewModel.periodStatistics.collectLatest {
                if (it != null) {
                    binding.progressBar.hide()
                    adapter.submitData(it)
                } else {
                    binding.progressBar.show()
                }
            }
        }


    }

    private fun initListeners() {
        requireActivity().supportFragmentManager.addOnBackStackChangedListener {
            if (isVisible) {
                requireActivity().enterFullScreen()
            }
        }
    }

    private fun initViews() {
        implementButtonDateRange()
        binding.recyclerView.adapter = adapter
    }

    private fun navigateToDayFragment(dayUsageStatistics: DayUsageStatistics) {
        addFragmentToBackStack(
            requireActivity().supportFragmentManager,
            R.id.fragmentContainerView,
            DayFragment(dayUsageStatistics)
        )
    }


    private fun setAccepted() {
        //show dialog
    }

    private fun implementButtonDateRange() {


        val constrainBuilder = CalendarConstraints.Builder()
            .setValidator(DateValidatorPointBackward.now()).build()

        binding.buttonDateRange.setOnClickListener {
            val picker = MaterialDatePicker.Builder.dateRangePicker()
                .setTheme(R.style.RangeCalendarTheme)
                .setCalendarConstraints(constrainBuilder)
                .setTitleText("Kúnler aralıǵıń saylań")
                .build()


            picker.show(this.childFragmentManager, "TAG")

            picker.addOnPositiveButtonClickListener {
                val fromDate = convertTimeToDate(it.first)
                val toDate = convertTimeToDate(it.second)

                binding.buttonDateRange.text =
                    "$fromDate - $toDate"
                lifecycleScope.launch {
                    Log.d("StatisticsFragment", "$fromDate - $toDate" )
                    userViewModel.getPeriodStatistics(
                        convertTimeToDate(it.first),
                        convertTimeToDate(it.second)
                    )
                }
            }

            picker.addOnNegativeButtonClickListener {
                picker.dismiss()
            }
        }

    }

    private fun getLastDayRange(lastDays: Int): Pair<String, String> {
        val calendar = Calendar.getInstance()
        val currentDate = Date()
        calendar.time = currentDate
        val tenDaysAgo = Calendar.getInstance()
        tenDaysAgo.time = currentDate
        tenDaysAgo.add(Calendar.DAY_OF_MONTH, lastDays)

        return Pair(formatDateToText(tenDaysAgo.time), formatDateToText(currentDate))
    }


    private fun formatDateToText(date: Date): String {
        val calendar = Calendar.getInstance()
        calendar.time = date
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH) + 1
        val year = calendar.get(Calendar.YEAR)

        return "$year-$month-$day"
    }

    private fun convertTimeToDate(time: Long): String {
        val utc = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        utc.timeInMillis = time
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return format.format(utc.time)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onClick(dayUsageStatistics: DayUsageStatistics) {
        navigateToDayFragment(dayUsageStatistics)
    }
}