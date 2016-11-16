package com.yeungkc.gank.io.ui.activity

import android.os.Bundle
import android.support.v4.view.WindowInsetsCompat
import com.yeungkc.gank.io.ui.IToolbarManager

abstract class BaseToolBarActivity : BaseActivity(), IToolbarManager {
    override var lastTime: Long = 0

    override fun initView(savedInstanceState: Bundle?) {
        initActionBar(savedInstanceState)
    }

    open fun initActionBar(savedInstanceState: Bundle?) {
        setSupportActionBar(getToolBar())
    }

    override fun onApplyWindowInsets(savedInstanceState: Bundle?, insets: WindowInsetsCompat) {
        super.onApplyWindowInsets(savedInstanceState, insets)
        getToolBar().run {
            setPadding(paddingLeft, insets.systemWindowInsetTop, paddingRight, paddingBottom)
        }

    }
}

