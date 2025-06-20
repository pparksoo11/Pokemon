package com.soo.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.soo.presentation.fragment.FavoriteFragment
import com.soo.presentation.fragment.ListFragment

class MainPagerAdapter(activity: FragmentActivity): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ListFragment()
            1 -> FavoriteFragment()
            else -> throw IllegalArgumentException("Invalid page")
        }
    }

}