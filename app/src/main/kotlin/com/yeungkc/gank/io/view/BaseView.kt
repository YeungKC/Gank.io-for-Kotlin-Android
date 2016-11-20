package com.yeungkc.gank.io.view

interface BaseView<in T> {
    fun onLoading()
    fun onError(error: Throwable)
    fun setData(data:T)
}