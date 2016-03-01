package com.yeungkc.gank.io.view

import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment
import com.yeungkc.gank.io.model.bean.Result

/**
 * @项目名: kc
 * @包名: gank.io.kc.view
 * @作者: YeungKC
 *
 * @描述：TODO
 */
interface IView {
    val mActivity: Activity
    val mContext: Context

    fun onChange(results: List<Result>){}
    fun onPostRequestExecute()
    fun onRequestComplete()
    fun onRequestSuccess() {
    }
    fun onRequestError(){}
    fun onRequestFail() {
    }
    fun onNoInternet(){}
}
