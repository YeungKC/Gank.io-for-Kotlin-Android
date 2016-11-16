package com.yeungkc.gank.io.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View

abstract class BaseFragment : Fragment() {
    var statusBarHeight = 0
    var navigationBarHeight = 0

    companion object {
        const val STATUS_BAR_HEIGHT = "STATUS_BAR_HEIGHT"
        const val NAVIGATION_BAR_HEIGHT = "NAVIGATION_BAR_HEIGHT"
        const val DATASETS_KEY: String = "DATA_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initArgs(arguments)
    }

    open fun initArgs(arguments: Bundle) {
        statusBarHeight = arguments.getInt(STATUS_BAR_HEIGHT)
        navigationBarHeight = arguments.getInt(NAVIGATION_BAR_HEIGHT)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initActionBar(savedInstanceState)

        initView(view, savedInstanceState)

        initEvent(savedInstanceState)

        initData(savedInstanceState)
    }

    open fun initData(savedInstanceState: Bundle?) {
    }

    open fun initEvent(savedInstanceState: Bundle?) {
    }

    open fun initView(view: View, savedInstanceState: Bundle?) {
    }

    open fun initActionBar(savedInstanceState: Bundle?) {
    }
}
