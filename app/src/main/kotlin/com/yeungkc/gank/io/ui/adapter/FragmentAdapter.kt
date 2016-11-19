package com.yeungkc.gank.io.ui.adapter

import android.support.v4.app.Fragment
import com.yeungkc.gank.io.fragmentnavigatior.FragmentNavigatorAdapter
import com.yeungkc.gank.io.ui.fragment.DetailFragment
import com.yeungkc.gank.io.ui.fragment.GankFragment

class FragmentAdapter(val categorical: Array<String>, val statusBarHeight: Int, val navigationBarHeight: Int) : FragmentNavigatorAdapter {
    override fun onCreateFragment(position: Int): Fragment {
        return when (position) {
            0 -> DetailFragment.newInstance(statusBarHeight = statusBarHeight, navigationBarHeight = navigationBarHeight)
            else -> GankFragment.newInstance(categorical[position], statusBarHeight, navigationBarHeight)
        }
    }

    override fun getTag(position: Int): String = categorical[position]

    override fun getCount(): Int = categorical.size
}