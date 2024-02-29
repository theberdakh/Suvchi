package com.theberdakh.suvchi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.theberdakh.suvchi.data.local.pref.LocalPreferences
import com.theberdakh.suvchi.databinding.ActivityMainBinding
import com.theberdakh.suvchi.util.setOnlyLightMode
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setOnlyLightMode()

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val inflater = navHostFragment.navController.navInflater
        val loginGraph = inflater.inflate(R.navigation.login_nav)
        val parenGraph = inflater.inflate(R.navigation.parent_nav)

        if (LocalPreferences().isLoggedIn){
            navHostFragment.navController.graph = parenGraph
        } else {
            navHostFragment.navController.graph = loginGraph
        }
    }
}