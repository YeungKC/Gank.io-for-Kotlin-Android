package com.yeungkc.gank.io.model.repo.source.memory

import rx.Observable


interface IMemorySource<T> {
    var mData: T?
    fun getContent(): Observable<T> {
        return if (mData != null) Observable.just(mData) else Observable.empty()
    }
    fun cache(data: T){
        mData = data
    }
}
