package com.theberdakh.suvchi.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.theberdakh.suvchi.R
import com.theberdakh.suvchi.data.local.pref.LocalPreferences
import com.theberdakh.suvchi.databinding.FragmentLoginBinding
import com.theberdakh.suvchi.presentation.LoginViewModel
import com.theberdakh.suvchi.util.show
import com.theberdakh.suvchi.util.showToast
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = checkNotNull(_binding)
    private val viewModel by viewModel<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        toggleVisibilityLoginButton()
        initObservers()

        binding.loginButton.setOnClickListener {
            if (checkEditText(binding.passwordLayout, binding.passwordEditText) && checkEditText(binding.userNameLayout, binding.usernameEditText)) {
                lifecycleScope.launch {
                    viewModel.login(
                        binding.usernameEditText.text.toString(),
                        binding.passwordEditText.text.toString()
                    )
                }
            }
        }

        return binding.root
    }

    private fun toggleVisibilityLoginButton() {
        binding.passwordEditText.doAfterTextChanged {
            if (!it.isNullOrEmpty() && it.isNotBlank()) {
                binding.loginButton.show()
            }
        }
    }

    private fun checkEditText(
        textInputLayout: TextInputLayout,
        editText: TextInputEditText
    ): Boolean {

        editText.doAfterTextChanged {
            textInputLayout.error = null
        }

        return if (editText.text.toString().isBlank() || editText.text.toString().isEmpty()) {
            textInputLayout.error = null
            textInputLayout.error = "Maydon bósh bólishi mumkin emas"
            false
        } else {
            true
        }
    }

    private fun initObservers() {
        viewModel.responseIsSuccessful.onEach {
            LocalPreferences().isLoggedIn = true
            val navHostFragment =
                requireActivity().supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
            val inflater = navHostFragment.navController.navInflater
            val graph = inflater.inflate(R.navigation.parent_nav)
            navHostFragment.navController.graph = graph
        }.launchIn(lifecycleScope)

        viewModel.responseIsMessage.onEach {
            showToast(it)
        }.launchIn(lifecycleScope)

        viewModel.responseIsError.onEach {
            it.printStackTrace()
        }.launchIn(lifecycleScope)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}