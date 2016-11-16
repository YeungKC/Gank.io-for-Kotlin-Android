package com.yeungkc.gank.io.ui.adapter

interface IAdapter {
    companion object{
        val STATE_LOADMORE = -2
        val STATE_ERROR = -3
        val STATE_MSG = -4

        val TYPE_LOADMORE = -2
        val TYPE_ERROR = -3
        val TYPE_MSG = -4
    }

    var mShowLoadingMore: Boolean
    var mIsLoading: Boolean
    var mState: Int
}