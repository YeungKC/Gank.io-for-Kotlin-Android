package com.yeungkc.gank.io.model

import com.yeungkc.gank.io.model.bean.BaseResult
import rx.functions.Func1

class HttpResultFunc<T> : Func1<BaseResult<T>, T> {
    override fun call(t: BaseResult<T>): T {
        if (t.error) throw ApiException()
        return t.results
    }
}