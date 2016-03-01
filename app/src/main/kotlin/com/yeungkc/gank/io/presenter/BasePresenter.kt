package com.yeungkc.gank.io.presenter

import com.yeungkc.gank.io.view.IView

/**
 * Created by YeungKC on 16/2/24.
 *
 * @项目名: kc
 * @包名: gank.io.kc.presenter
 * @作者: YeungKC
 *
 * @描述：TODO
 */
interface  BasePresenter<V : IView>{
    val mView: V

    fun removeListener()
}

