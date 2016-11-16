package com.yeungkc.gank.io.view

interface PageView<T> : BaseView<T> {
    var requestPage:Int
    var currentPage:Int
    var isNoData:Boolean
}