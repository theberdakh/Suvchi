package com.theberdakh.suvchi.ui.dashboard

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.MPPointF
import com.theberdakh.suvchi.R
import com.theberdakh.suvchi.data.local.demo.AnalyticsDemo
import com.theberdakh.suvchi.databinding.FragmentDashboardBinding
import com.theberdakh.suvchi.util.showPopUpMenu


class DashboardFragment : Fragment() {
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)

        initListeners()
        initViews()


        return binding.root
    }

    private fun initViews() {
        val adapter= DashboardAdapter(){}
        adapter.submitList(AnalyticsDemo.getDemoResults())
        binding.recyclerView.adapter = adapter

        binding.chart.setUsePercentValues(true)
        binding.chart.getDescription().setEnabled(false)

        binding.chart.setDragDecelerationFrictionCoef(0.95f)


       binding.chart.setDrawHoleEnabled(true)
        binding.chart.setHoleColor(Color.WHITE)

        binding.chart.setTransparentCircleColor(Color.WHITE)
        binding.chart.setTransparentCircleAlpha(110)

        binding.chart.setHoleRadius(58f)
        binding.chart.setTransparentCircleRadius(61f)

        binding.chart.setDrawCenterText(true)

        // enable rotation of the chart by touch
        // enable rotation of the chart by touch
        binding.chart.setRotationEnabled(true)
        binding.chart.setHighlightPerTapEnabled(true)

        // chart.setUnit(" €");
        // chart.setDrawUnitsInChart(true);

        // add a selection listener

        // chart.setUnit(" €");
        // chart.setDrawUnitsInChart(true);

        // add a selection listener



        binding.chart.animateY(1400, Easing.EaseInOutQuad)
        // chart.spin(2000, 0, 360);

        // chart.spin(2000, 0, 360);
        val l: Legend = binding.chart.getLegend()
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP)
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT)
        l.setOrientation(Legend.LegendOrientation.VERTICAL)
        l.setDrawInside(false)
        l.setXEntrySpace(7f)
        l.setYEntrySpace(0f)
        l.setYOffset(0f)

        // entry label styling

        // entry label styling
        binding.chart.setEntryLabelColor(Color.WHITE)
        binding.chart.setEntryLabelTextSize(8f)


        val entries = ArrayList<PieEntry>()

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.

        entries.add(PieEntry(25f))
        entries.add(PieEntry(75f))


        val dataSet = PieDataSet(entries, "")
        dataSet.setDrawIcons(false)

        dataSet.setSliceSpace(0f)

        dataSet.setIconsOffset(MPPointF(0f, 50f))
        dataSet.selectionShift = 4f
        dataSet.isHighlightEnabled = true

        // add a lot of colors


        // add a lot of colors
        val colors = ArrayList<Int>()
        colors.add(ColorTemplate.rgb("#2ABCA8"))
        colors.add(ColorTemplate.rgb("#0980EC"))

        dataSet.colors = colors
        //dataSet.setSelectionShift(0f);

        //dataSet.setSelectionShift(0f);
        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter())
        data.setValueTextSize(12f)
        data.setValueTextColor(Color.WHITE)
        binding.chart.setData(data)

    }

    private fun initListeners() {
        binding.textviewDateRange.setOnClickListener {
            requireContext().showPopUpMenu(R.menu.menu_year, binding.textviewDateRange)
        }
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}