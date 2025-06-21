package com.soo.presentation.activity

import androidx.viewpager2.widget.ViewPager2
import com.soo.presentation.R
import com.soo.presentation.adapter.MainPagerAdapter
import com.soo.presentation.base.BaseActivity
import com.soo.presentation.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private lateinit var pagerAdapter: MainPagerAdapter

    override fun initView() {
        setupNavigation()
    }

    private fun setupNavigation() {
        val pagerAdapter = MainPagerAdapter(this)
        binding.viewPager.adapter = pagerAdapter
        binding.viewPager.isUserInputEnabled = false

        binding.navigation.setOnItemSelectedListener { item ->
            val index = when (item.itemId) {
                R.id.listFragment -> 0
                R.id.favoriteFragment -> 1
                else -> 0 // 기본값 설정
            }

            binding.viewPager.setCurrentItem(index, false)
            true
        }

        binding.viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    0 -> binding.navigation.selectedItemId = R.id.listFragment
                    1 -> {
                        binding.navigation.selectedItemId = R.id.favoriteFragment
                    }
                }
            }
        })
    }
}