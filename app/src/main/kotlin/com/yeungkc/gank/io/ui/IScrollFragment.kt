package com.yeungkc.gank.io.ui

interface IScrollFragment {
    companion object{
        const val SCROLL_OFF_SET_KEY: String = "SCROLL_OFF_SET_KEY"
    }
    fun checkPaddingTop()
    fun onDoubleClickToolBar()
}