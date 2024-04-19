package com.theberdakh.suvchi.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.theberdakh.suvchi.R
import com.theberdakh.suvchi.data.local.pref.LocalPreferences
import com.theberdakh.suvchi.data.remote.LoginApi
import com.theberdakh.suvchi.data.remote.model.auth.LoginRequest
import com.theberdakh.suvchi.data.remote.model.auth.LoginResponse
import com.theberdakh.suvchi.data.remote.utils.isOnline
import com.theberdakh.suvchi.databinding.FragmentMainBinding
import com.theberdakh.suvchi.presentation.LoginViewModel
import com.theberdakh.suvchi.ui.settings.SettingsFragment
import com.theberdakh.suvchi.ui.water_usage.StatisticsFragment
import com.theberdakh.suvchi.ui.contracts.MessageFragment
import com.theberdakh.suvchi.ui.dashboard.DashboardFragment
import com.theberdakh.suvchi.util.replaceFragment
import com.theberdakh.suvchi.util.showToast
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.ext.android.viewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = checkNotNull(_binding)
    private val viewModel by viewModel<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        initViews()
        initListeners()
        initObservers()


        return binding.root
    }

    private fun initListeners() {
        binding.bottomNavView.setOnItemSelectedListener { menuItem ->

            val fragment = when (menuItem.itemId) {
                R.id.bottom_action_message -> MessageFragment()
                R.id.bottom_action_dashboard -> DashboardFragment()
                R.id.bottom_action_statistics -> StatisticsFragment()
                else -> SettingsFragment()
            }

            replaceFragment(
                childFragmentManager,
                R.id.nested_fragment_container,
                fragment = fragment
            )
            true
        }
    }

    private fun initViews() {
        replaceFragment(childFragmentManager, R.id.nested_fragment_container, DashboardFragment())
    }

    private fun initObservers() {
        //Manual handling of refresh token when access token revokes
        lifecycleScope.launch {
            if (requireContext().isOnline()){
                val response = try {
                    RetrofitInstance.api.login(
                        LoginRequest(
                            LocalPreferences().username,
                            LocalPreferences().password
                        )
                    )
                } catch (e: IOException) {
                    showToast(getString(R.string.check_network_connection))
                    return@launch
                }

                if (response.isSuccessful && response.body() != null) {
                    refreshTokens(response.body()!!)
                } else {
                    showToast(getString(R.string.error_text_response_is_error_in_login))
                }
            } else {
                showToast(getString(R.string.check_network_connection))
            }


        }

        viewModel.responseIsSuccessful.onEach {
            Log.d("MainFragment", "Response token ${it.accessToken}")
            LocalPreferences().accessToken = it.accessToken
            LocalPreferences().refreshToken = it.refreshToken
        }.launchIn(lifecycleScope)

        viewModel.responseIsMessage.onEach {
            showToast(it)
        }

        viewModel.responseIsError.onEach {
            showToast(getString(R.string.error_text_response_is_error_in_login))
        }
    }

    private fun refreshTokens(loginResponse: LoginResponse) {
        LocalPreferences().accessToken = loginResponse.accessToken
        LocalPreferences().refreshToken = loginResponse.refreshToken
    }


    private object RetrofitInstance {

        val httpLoggingInterceptor = HttpLoggingInterceptor().setLevel(
            HttpLoggingInterceptor.Level.BODY
        )

        val client = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor { chain: Interceptor.Chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer ${LocalPreferences().refreshToken}")
                    .build()
                chain.proceed(request)
            }
            .build()

        val api: LoginApi by lazy {
            Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.smartwaterdegree.uz")
                .client(client)
                .build()
                .create(LoginApi::class.java)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}