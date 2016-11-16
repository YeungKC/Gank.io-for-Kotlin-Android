package com.yeungkc.gank.io.model.repo.source.remote

import rx.Observable

interface IRemoteSource<T,in P> {
    fun requestContent(param: P): Observable<T>
}