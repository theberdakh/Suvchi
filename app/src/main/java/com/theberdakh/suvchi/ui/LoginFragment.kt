package com.theberdakh.suvchi.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.theberdakh.suvchi.R
import com.theberdakh.suvchi.data.local.pref.LocalPreferences
import com.theberdakh.suvchi.databinding.FragmentLoginBinding
import com.theberdakh.suvchi.util.show

class LoginFragment: Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = checkNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.passwordEditText.doAfterTextChanged {
            if (!it.isNullOrEmpty() && it.isNotBlank()){
                binding.loginButton.show()
            }
        }

        binding.loginButton.setOnClickListener {
            // add password, login validator?
            LocalPreferences().isLoggedIn = true
            val navHostFragment = requireActivity().supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
            val inflater = navHostFragment.navController.navInflater
            val graph = inflater.inflate(R.navigation.parent_nav)
            navHostFragment.navController.graph = graph


        }

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}