package com.yeungkc.gank.io.model.repo

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.Target
import com.yeungkc.gank.io.CustomApplication
import com.yeungkc.gank.io.model.bean.Result
import com.yeungkc.gank.io.model.repo.source.data.GankDataSource
import com.yeungkc.gank.io.model.repo.source.remote.GankRemoteSource
import com.yeungkc.gank.io.model.repo.source.remote.RequestBean
import rx.Observable

class GankRepo(categorical: String) : IRepo<List<Result>,  RequestBean, GankRemoteSource, GankDataSource> {
    override val mRemoteSource: GankRemoteSource = GankRemoteSource()
    override val mDataSource: GankDataSource = GankDataSource(categorical)

    override fun getRemoteContent(p: RequestBean): Observable<List<Result>> {
        return mRemoteSource.requestContent(p)
                .flatMap { Observable.from(it) }
                .map {
                    if (it.type == "福利") {
                        val bitmap = Glide.with(CustomApplication.contenxt)
                                .load(it.url)
                                .asBitmap()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                                .get()

                        it.width = bitmap.width
                        it.height = bitmap.height
                    }
                    it
                }
                .toList()
//                .doOnNext { mDataSource.save(it) }
//                .doOnNext { mMemorySource.cache(it) }
    }
}
