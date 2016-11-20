package com.yeungkc.gank.io.ui.databinding.adapter

import android.databinding.BindingAdapter
import android.support.v4.widget.SwipeRefreshLayout

class SwipeRefreshLayoutAdapter {
    @BindingAdapter("app:colorSchemeColor")
    fun setColorSchemeColor(swipeRefreshLayout: SwipeRefreshLayout, color: Int) {
        swipeRefreshLayout.setColorSchemeColors(color)
    }
}