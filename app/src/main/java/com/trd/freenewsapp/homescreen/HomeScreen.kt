package com.trd.freenewsapp.homescreen

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.trd.freenewsapp.R
import com.trd.freenewsapp.constants.Constants.LOG_TAG
import com.trd.freenewsapp.databinding.ActivityHomeScreenBinding
import androidx.navigation.ui.setupActionBarWithNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeScreen : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var binding: ActivityHomeScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeBottomNavigation()
//        initNavigation()
    }

/*    private fun initializeBottomNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
//        binding.bottomNavBar.setOnItemReselectedListener { item ->
        binding.bottomNavBar.setOnItemReselectedListener { item ->
            Log.i(LOG_TAG, "HOMESCREEN initializeBottomNavigation() item = ${item.itemId}")
            if (item.itemId != binding.bottomNavBar.selectedItemId) {
                NavigationUI.onNavDestinationSelected(item, navController)
            }
        }
        binding.bottomNavBar.setupWithNavController(navController)
    }*/

    private fun initializeBottomNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
//        binding.bottomNavBar.setOnItemReselectedListener { item ->
//        binding.bottomNavBar.setOnNavigationItemSelectedListener  { item ->
        binding.bottomNavBar.setOnItemSelectedListener  { item ->
            Log.i(LOG_TAG, "HOMESCREEN initializeBottomNavigation() item = ${item.itemId}")
            NavigationUI.onNavDestinationSelected(item, navController)
        }
        binding.bottomNavBar.setupWithNavController(navController)
    }




}