package com.theberdakh.suvchi.ui.main

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayout
import com.theberdakh.suvchi.R
import com.theberdakh.suvchi.data.local.pref.LocalPreferences
import com.theberdakh.suvchi.data.remote.LoginApi
import com.theberdakh.suvchi.data.remote.model.auth.LoginRequest
import com.theberdakh.suvchi.data.remote.model.auth.LoginResponse
import com.theberdakh.suvchi.data.remote.utils.isOnline
import com.theberdakh.suvchi.databinding.FragmentMainBinding
import com.theberdakh.suvchi.presentation.LoginViewModel
import com.theberdakh.suvchi.ui.settings.SettingsFragment
import com.theberdakh.suvchi.ui.statistics.StatisticsFragment
import com.theberdakh.suvchi.ui.contracts.MessageFragment
import com.theberdakh.suvchi.ui.dashboard.DashboardFragment
import com.theberdakh.suvchi.util.enterFullScreen
import com.theberdakh.suvchi.util.exitFullScreen
import com.theberdakh.suvchi.util.hide
import com.theberdakh.suvchi.util.replaceFragment
import com.theberdakh.suvchi.util.show
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

        binding.tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val position = tab?.position

                val fragment = when (position) {
                    0 -> {
                        val firstName = LocalPreferences().getUserData().firstName
                        val lastName = LocalPreferences().getUserData().lastName
                        binding.toolbar.show()
                        binding.appBarLayout.setBackgroundResource(R.drawable.header_rectangle)
                        binding.profileInfo.hide()
                        binding.toolbar.title =  getString(R.string.profile_name, firstName, lastName)
                        DashboardFragment()
                    }
                    1 -> {
                        binding.toolbar.show()
                        binding.profileInfo.hide()
                        binding.appBarLayout.setBackgroundResource(R.drawable.header_rectangle_collapsed)
                        binding.toolbar.setTitle(R.string.daily_statistics)
                        StatisticsFragment()
                    }
                    2 -> {
                        binding.toolbar.show()
                        binding.profileInfo.hide()
                        binding.appBarLayout.setBackgroundResource(R.drawable.header_rectangle_collapsed)
                        binding.toolbar.setTitle(getString(R.string.messages))
                        MessageFragment()
                    }
                    else -> {
                        binding.appBarLayout.setBackgroundResource(R.drawable.header_rectangle)
                        binding.toolbar.hide()
                        binding.profileInfo.show()
                        implementProfileInfo()
                        SettingsFragment()
                    }
                }

                replaceFragment(
                    childFragmentManager,
                    R.id.nested_fragment_container,
                    fragment = fragment
                )


            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                Log.d("TabLAyout", "Tab unselected")
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })


    }

    private fun initViews() {
        requireActivity().enterFullScreen()
        replaceFragment(childFragmentManager, R.id.nested_fragment_container, DashboardFragment())
        binding.tabLayout.tabRippleColor = null

        val firstName = LocalPreferences().getUserData().firstName
        val lastName = LocalPreferences().getUserData().lastName

        binding.appBarLayout.setBackgroundResource(R.drawable.header_rectangle)
        binding.toolbar.title = "$firstName $lastName"
        binding.profileInfo.hide()




    }

    private fun implementProfileInfo() {
        val firstName = LocalPreferences().getUserData().firstName
        val lastName = LocalPreferences().getUserData().lastName
        val phone = LocalPreferences().getUserData().phone
        val image = LocalPreferences().getUserData().avatar

        binding.tvName.text = getString(R.string.profile_name, firstName, lastName)
        binding.tvPhone.text = getString(R.string.phone_number, phone)
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onDestroyView() {
        _binding = null
        requireActivity().exitFullScreen()
        super.onDestroyView()
    }
}