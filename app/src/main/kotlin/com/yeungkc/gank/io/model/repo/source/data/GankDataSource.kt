package com.yeungkc.gank.io.model.repo.source.data

import com.yeungkc.gank.io.model.bean.Result

class GankDataSource(val mCategorical: String) : IDataSource<List<Result>> {
//    override fun getContent(getCount: () -> Int ): Observable<List<Result>> {
//        return Realm.getDefaultInstance().run {
//            where(Result::class.java)
//                    .equalTo("type", mCategorical)
//                    .findAllSortedAsync("publishedAt", Sort.DESCENDING)
//                    .asObservable()
//                    .filter { it.isLoaded }
//                    .map {
//                        val count = getCount()
//
//                        if (count > 0) {
//                            return@map it.take(count)
//                        }
//                        it
//                    }
//                    .map { copyFromRealm(it) }
//                    .onBackpressureDrop()
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .doOnUnsubscribe { close() }
//        }
//    }
}