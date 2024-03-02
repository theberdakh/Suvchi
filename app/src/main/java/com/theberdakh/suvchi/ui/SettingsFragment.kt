package com.theberdakh.suvchi.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.theberdakh.suvchi.R
import com.theberdakh.suvchi.data.local.pref.LocalPreferences
import com.theberdakh.suvchi.databinding.FragmentSettingsBinding
import com.theberdakh.suvchi.databinding.FragmentStatisticsBinding
import com.theberdakh.suvchi.presentation.UserViewModel
import com.theberdakh.suvchi.util.showSnackbar
import com.theberdakh.suvchi.util.showToast
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment: Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = checkNotNull(_binding)
    private val viewModel by viewModel<UserViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)

        initObservers()

        val firstName = LocalPreferences().getUserData().firstName
        val lastName = LocalPreferences().getUserData().lastName
        val phone = LocalPreferences().getUserData().phone

        binding.textviewProfileName.text = "$firstName $lastName"
        binding.textviewProfileType.text = "+$phone"


        binding.layoutLogOut.setOnClickListener {
            LocalPreferences().clearUserData()
            val navHostFragment = requireActivity().supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
            val inflater = navHostFragment.navController.navInflater
            val graph = inflater.inflate(R.navigation.login_nav)
            navHostFragment.navController.graph = graph
        }

        return binding.root
    }

    private fun initObservers() {
        lifecycleScope.launch {
          //  viewModel.getUserProfile()
        }

        viewModel.userProfileResponseSuccessful.onEach {
            binding.textviewProfileName.text = "${it.firstName} ${it.lastName}"
            binding.textviewProfileType.text = it.phone
        }.launchIn(lifecycleScope)

        viewModel.userProfileResponseMessage.onEach {
            showToast(it)
        }.launchIn(lifecycleScope)

        viewModel.userProfileResponseError.onEach {
            showToast(it.message.toString())
        }.launchIn(lifecycleScope)
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}