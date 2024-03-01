package com.theberdakh.suvchi.ui

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

class OverviewFragment : Fragment() {
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textviewDateRange.setOnClickListener {
            showPopUp(R.menu.menu_year, binding.textviewDateRange)
        }
        val adapter= DashboardAdapter(){
            showPopUp(R.menu.menu_month, it)
        }
        adapter.submitList(AnalyticsDemo.getDemoResults())
        binding.recyclerView.adapter = adapter

    }

    private fun showPopUp(@MenuRes id: Int, view: View) {
        val popUp = PopupMenu(requireContext(), view)
        val inflater = popUp.menuInflater
        inflater.inflate(id, popUp.menu)

        popUp.setOnMenuItemClickListener {
            true
        }

        popUp.show()

    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}