package com.theberdakh.suvchi.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.theberdakh.suvchi.R
import com.theberdakh.suvchi.databinding.FragmentMainBinding
import com.theberdakh.suvchi.util.replaceFragment

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = checkNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)


        setUpBottomNav()
        replaceFragment(childFragmentManager, R.id.nested_fragment_container, OverviewFragment())


        return binding.root
    }

    private fun setUpBottomNav() {
        binding.bottomNavView.setOnItemSelectedListener { menuItem ->

            val nestedFragment = when (menuItem.itemId) {
                R.id.bottom_action_message -> MessageFragment()
                R.id.bottom_action_settings -> SettingsFragment()
                R.id.bottom_action_dashboard -> OverviewFragment()
                R.id.bottom_action_document -> SettingsFragment()
                else -> throw Exception("Not found fragment")
            }
            replaceFragment(childFragmentManager, R.id.nested_fragment_container, nestedFragment)

            true
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}