package com.theberdakh.suvchi.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.theberdakh.suvchi.R
import com.theberdakh.suvchi.data.local.pref.LocalPreferences
import com.theberdakh.suvchi.databinding.FragmentSettingsBinding
import com.theberdakh.suvchi.databinding.FragmentStatisticsBinding
import com.theberdakh.suvchi.util.showSnackbar

class SettingsFragment: Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = checkNotNull(_binding)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        binding.layoutLogOut.setOnClickListener {
            LocalPreferences().isLoggedIn = false
            val navHostFragment = requireActivity().supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
            val inflater = navHostFragment.navController.navInflater
            val graph = inflater.inflate(R.navigation.login_nav)
            navHostFragment.navController.graph = graph
        }

        return binding.root
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}