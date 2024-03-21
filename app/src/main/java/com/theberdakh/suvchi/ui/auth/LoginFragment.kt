package com.theberdakh.suvchi.ui.auth

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
import com.theberdakh.suvchi.presentation.UserViewModel
import com.theberdakh.suvchi.util.getString
import com.theberdakh.suvchi.util.getStringIsEmptyOrBlank
import com.theberdakh.suvchi.util.hide
import com.theberdakh.suvchi.util.shakeIfEmptyOrBlank
import com.theberdakh.suvchi.util.show
import com.theberdakh.suvchi.util.showToast
import com.theberdakh.suvchi.util.vibratePhone
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = checkNotNull(_binding)
    private val viewModel by viewModel<LoginViewModel>()
    private val userViewModel by viewModel<UserViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        initViews()
        initListeners()
        initObservers()



        return binding.root
    }

    private fun initListeners() {
        binding.passwordEditText.doAfterTextChanged {
            if (!binding.passwordEditText.getStringIsEmptyOrBlank()) {
                binding.loginButton.show()
            }
        }


        binding.loginButton.setOnClickListener {
            val isUsernameValid = binding.usernameEditText.shakeIfEmptyOrBlank()
            val isPasswordValid = binding.passwordEditText.shakeIfEmptyOrBlank()

            if (isPasswordValid && isUsernameValid) {
                lifecycleScope.launch {
                    viewModel.login(
                        binding.usernameEditText.text.toString(),
                        binding.passwordEditText.text.toString()
                    )
                }
            } else {
                requireActivity().vibratePhone()
            }
        }

    }

    private fun initViews() {
        binding.loginButton.hide()
    }

    private fun initObservers() {
        viewModel.responseIsSuccessful.onEach { loginResponse ->
            LocalPreferences().username = binding.usernameEditText.text.toString()
            LocalPreferences().password = binding.passwordEditText.text.toString()
            LocalPreferences().saveUserToken(loginResponse)
            userViewModel.getUserProfile()
            getUserProfileData()
        }.launchIn(lifecycleScope)

        viewModel.responseIsMessage.onEach {
            showToast(it)
        }.launchIn(lifecycleScope)

        viewModel.responseIsError.onEach {
            it.printStackTrace()
        }.launchIn(lifecycleScope)
    }

    private fun getUserProfileData() {

        userViewModel.userProfileResponseSuccessful.onEach { userResponse ->
            LocalPreferences().saveUserResponse(userResponse)
            val navHostFragment = requireActivity().supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
            val inflater = navHostFragment.navController.navInflater
            val graph = inflater.inflate(R.navigation.parent_nav)
            navHostFragment.navController.graph = graph

        }.launchIn(lifecycleScope)

        userViewModel.userProfileResponseMessage.onEach {
            showToast(it)
        }.launchIn(lifecycleScope)

        userViewModel.userProfileResponseError.onEach {
            showToast(it.message.toString())
        }.launchIn(lifecycleScope)

    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}