package com.yeungkc.gank.io.model.repo.source.remote

import com.yeungkc.gank.io.model.HttpResultFunc
import com.yeungkc.gank.io.model.bean.Result
import com.yeungkc.gank.io.model.service.GankService
import rx.Observable
import rx.schedulers.Schedulers

class GankRemoteSource : IRemoteSource<List<Result>, RequestBean> {
    override fun requestContent(param: RequestBean): Observable<List<Result>> {
        return GankService.api.categoricalData(param.type, param.page, param.limit)
                .subscribeOn(Schedulers.io())
                .map(HttpResultFunc<List<Result>>())
    }
}


class RequestBean(
        val type: String,
        val page: Int,
        val limit: Int
)
