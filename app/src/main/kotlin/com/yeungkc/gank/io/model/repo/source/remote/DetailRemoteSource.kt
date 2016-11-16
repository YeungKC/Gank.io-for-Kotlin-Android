package com.yeungkc.gank.io.model.repo.source.remote

import com.yeungkc.gank.io.extensions.day
import com.yeungkc.gank.io.extensions.month
import com.yeungkc.gank.io.extensions.year
import com.yeungkc.gank.io.model.HttpResultFunc
import com.yeungkc.gank.io.model.bean.DayResult
import com.yeungkc.gank.io.model.bean.Result
import com.yeungkc.gank.io.model.service.GankService
import rx.Observable
import rx.schedulers.Schedulers
import java.util.*

class DetailRemoteSource : IRemoteSource<List<Result>, Boolean> {
    override fun requestContent(param: Boolean): Observable<List<Result>> {
        return GankService.api.getHistory()
                .subscribeOn(Schedulers.io())
                .map(HttpResultFunc<List<Date>>())
                .flatMap {
                    it[0].run {
                        GankService.api.getDetailData(year(), month(), day())
                    }
                }
                .map(HttpResultFunc<DayResult>())
                .map {
                    it.run {
                        androidList +
                                iosList +
                                前端List +
                                appList +
                                瞎推荐List +
                                拓展资源List +
                                休息视频List
                    }
                }
    }
}