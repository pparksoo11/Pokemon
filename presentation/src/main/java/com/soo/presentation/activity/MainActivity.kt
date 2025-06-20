package com.soo.presentation.activity

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.soo.presentation.R
import com.soo.presentation.base.BaseActivity
import com.soo.presentation.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun initView() {
        setupNavigation()
    }

    private fun setupNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as? NavHostFragment ?: return
        val navController = navHostFragment.navController
        binding.navigation.setupWithNavController(navController)
    }
}