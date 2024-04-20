package com.theberdakh.suvchi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.theberdakh.suvchi.data.local.pref.LocalPreferences
import com.theberdakh.suvchi.data.remote.utils.isOnline
import com.theberdakh.suvchi.databinding.ActivityMainBinding
import com.theberdakh.suvchi.util.checkHostResolution
import com.theberdakh.suvchi.util.setOnlyLightMode
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setOnlyLightMode()

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val inflater = navHostFragment.navController.navInflater
        val loginGraph = inflater.inflate(R.navigation.login_nav)
        val parenGraph = inflater.inflate(R.navigation.parent_nav)

        navHostFragment.navController.graph = parenGraph

/*
        lifecycleScope.launch {
            if (this@MainActivity.isOnline()){
                if (checkHostResolution(this@MainActivity, "api.smartwaterdegree.uz")) {
                    navHostFragment.navController.graph =
                        if (LocalPreferences().isUserLoggedIn()) parenGraph else loginGraph
                } else {
                    navHostFragment.navController.graph = inflater.inflate(R.navigation.login_nav)
                }
            }

        }*/

    }
}