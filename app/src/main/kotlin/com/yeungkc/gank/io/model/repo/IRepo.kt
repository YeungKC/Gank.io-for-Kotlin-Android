package com.yeungkc.gank.io.model.repo

import com.yeungkc.gank.io.model.repo.source.data.IDataSource
import com.yeungkc.gank.io.model.repo.source.remote.IRemoteSource
import rx.Observable

interface IRepo<T , in P, out Remote : IRemoteSource<T, P>, out Data : IDataSource<T>> {
    val mRemoteSource: Remote
    val mDataSource: Data

//    fun getDataContent(getCount: () -> Int = { 0 }): Observable<T> = mDataSource.getContent(getCount)
//                .doOnNext { mMemorySource.cache(it) }

//    fun getContent(getCount: () -> Int = {0}): Observable<T>
//            =
//            Observable.concat(
//                    getCacheContent(),
//                    getDataContent(getCount)
//            )


    fun getRemoteContent(p: P): Observable<T>
            = mRemoteSource.requestContent(p)
//            .doOnNext { mDataSource.save(it) }


//    fun save(data: T) = mDataSource.save(data)
}