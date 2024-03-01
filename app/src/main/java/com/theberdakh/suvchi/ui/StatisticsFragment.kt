package com.theberdakh.suvchi.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.theberdakh.suvchi.R
import com.theberdakh.suvchi.data.local.demo.AnalyticsDemo
import com.theberdakh.suvchi.databinding.FragmentStatisticsBinding
import com.theberdakh.suvchi.util.addFragmentToBackStack
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class StatisticsFragment : Fragment() {
    private var _binding: FragmentStatisticsBinding? = null
    private val binding get() = checkNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatisticsBinding.inflate(inflater, container, false)

        implementButtonDateRange()

        val adapter = DayAdapter(
            { setAccepted() },
            { setDeclined() },
            {setCardClicked()}
        )

        binding.recyclerView.adapter = adapter
        adapter.submitList(AnalyticsDemo.getDemoStatsForWeek())



        return binding.root
    }

    private fun setCardClicked(){
        addFragmentToBackStack(
            requireActivity().supportFragmentManager,
            R.id.fragmentContainerView,
            DayFragment()
        )
    }
    private fun setDeclined() {
        showDialog(R.layout.dialog_decline)
        //show dialog
    }

    private fun showDialog(@LayoutRes id: Int) {
        val dialog = BottomSheet()

        dialog.show(childFragmentManager, "Tag")

    }

    private fun setAccepted() {
        //show dialog
    }

    private fun implementButtonDateRange() {
        binding.buttonDateRange.text = getLastDayRange(10)
        binding.buttonDateRange.setOnClickListener {
            val picker = MaterialDatePicker.Builder.dateRangePicker()
                .setTheme(R.style.RangeCalendarTheme)
                .setTitleText("Kúnler aralıǵın saylań")
                .build()

            picker.show(this.childFragmentManager, "TAG")

            picker.addOnPositiveButtonClickListener {
                binding.buttonDateRange.text =
                    "${convertTimeToDate(it.first)} -${convertTimeToDate(it.second)}"
            }

            picker.addOnNegativeButtonClickListener {
                picker.dismiss()
            }
        }

    }

    private fun getLastDayRange(lastDays: Int): String {
        val calendar = Calendar.getInstance()
        val currentDate = Date()
        calendar.time = currentDate
        val tenDaysAgo = Calendar.getInstance()
        tenDaysAgo.time = currentDate
        tenDaysAgo.add(Calendar.DAY_OF_MONTH, lastDays)

        return "${formatDateToText(tenDaysAgo.time)} - ${formatDateToText(currentDate)}"
    }


    private fun formatDateToText(date: Date): String {
        val calendar = Calendar.getInstance()
        calendar.time = date
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH) + 1
        val year = calendar.get(Calendar.YEAR)

        return "$day/$month/$year"
    }

    private fun formatDateToLong(date: Date): Long {
        return date.time
    }

    private fun convertTimeToDate(time: Long): String {
        val utc = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        utc.timeInMillis = time
        val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return format.format(utc.time)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}