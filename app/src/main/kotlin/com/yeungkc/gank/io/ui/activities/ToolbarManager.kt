package com.yeungkc.gank.io.ui.activities

import android.support.v7.graphics.drawable.DrawerArrowDrawable
import android.support.v7.widget.Toolbar

interface ToolbarManager {

    val toolbar: Toolbar
    var lastTime: Long

    var toolbarTitle: String
        get() = toolbar.title.toString()
        set(value) {
            toolbar.title = value
        }

    fun enableDoubleClickToEvent(event: () -> Unit) {
        toolbar.setOnClickListener {
            val nowTime = System.currentTimeMillis()
            if ( (nowTime - lastTime ) > 2000) lastTime = nowTime else event()
        }
    }

    fun enableHomeAsUp(up: () -> Unit) {
        toolbar.navigationIcon = createUpDrawable()
        toolbar.setNavigationOnClickListener { up() }
    }

    private fun createUpDrawable() = DrawerArrowDrawable(toolbar.context).apply { progress = 1f }
}