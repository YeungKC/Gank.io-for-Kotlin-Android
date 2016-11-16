package com.yeungkc.gank.io.fragmentnavigatior

import android.support.v4.app.Fragment

interface FragmentNavigatorAdapter {
    fun onCreateFragment(position: Int): Fragment

    fun getTag(position: Int): String

    fun getCount(): Int
}