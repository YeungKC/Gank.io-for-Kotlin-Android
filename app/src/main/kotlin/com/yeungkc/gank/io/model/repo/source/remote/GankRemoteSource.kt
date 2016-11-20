package com.yeungkc.gank.io.model.repo.source.remote

import com.yeungkc.gank.io.model.HttpResultFunc
import com.yeungkc.gank.io.model.bean.Result
import com.yeungkc.gank.io.model.service.GankService
import rx.Observable
import rx.schedulers.Schedulers
import ws.vinta.pangu.Pangu

class GankRemoteSource : IRemoteSource<List<Result>, RequestBean> {
    val pangu by lazy { Pangu() }

    override fun requestContent(param: RequestBean): Observable<List<Result>> {
        return GankService.api.categoricalData(param.type, param.page, param.limit)
                .subscribeOn(Schedulers.io())
                .map(HttpResultFunc<List<Result>>())
                .flatMap { Observable.from(it) }
                .map {
                    it.desc = pangu.spacingText(it.desc)
                    it
                }
                .toList()
    }
}


class RequestBean(
        val type: String,
        val page: Int,
        val limit: Int
)
