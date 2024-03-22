package com.theberdakh.suvchi.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.annotation.MenuRes
import androidx.fragment.app.Fragment
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