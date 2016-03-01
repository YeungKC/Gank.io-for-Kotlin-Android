package com.yeungkc.gank.io.model.server

import com.yeungkc.gank.io.model.BaseRequestCallBack
import com.yeungkc.gank.io.model.bean.BaseResult
import com.yeungkc.gank.io.view.IView

/**
 * Created by YeungKC on 16/2/27.

 * @项目名: kc
 * *
 * @包名: gank.io.kc.model.server
 * *
 * @作者: YeungKC
 * *
 * @描述：TODO
 */
abstract class GankRequestCallBack<B : BaseResult<*>>(mV: IView) : BaseRequestCallBack<B>(mV) {
    override fun isSuccess(responseBean: B): Boolean {
        return responseBean.error
    }
}