package com.yeungkc.gank.io.ui.activity

import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.support.v4.view.WindowInsetsCompat
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import com.yeungkc.gank.io.extensions.isOrientationPortrait
import org.jetbrains.anko.contentView

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initArgs(savedInstanceState)

        onCreateView()

        val orientationPortrait = isOrientationPortrait()
        onLandscapeClearTranslucentNavigation(orientationPortrait)

        initView(savedInstanceState)

        initEvent(savedInstanceState)

        initData(savedInstanceState)
    }

    abstract fun onCreateView()

    open fun initArgs(savedInstanceState: Bundle?) {
    }

    open fun initData(savedInstanceState: Bundle?) {

    }

    open fun initEvent(savedInstanceState: Bundle?) {
        ViewCompat.setOnApplyWindowInsetsListener(contentView) { view, insets ->
            onApplyWindowInsets(savedInstanceState, insets)
            ViewCompat.setOnApplyWindowInsetsListener(contentView, null)
            insets.consumeStableInsets()
        }
    }

    open fun initView(savedInstanceState: Bundle?) {

    }

    open fun onApplyWindowInsets(savedInstanceState: Bundle?, insets: WindowInsetsCompat) {
    }

    private fun onLandscapeClearTranslucentNavigation(orientationPortrait: Boolean) {
        if (!orientationPortrait) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        }
    }
}