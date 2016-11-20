package com.yeungkc.gank.io.contract

import android.support.annotation.UiThread
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.Target
import com.yeungkc.gank.io.CustomApplication
import com.yeungkc.gank.io.extensions.pending
import com.yeungkc.gank.io.model.bean.Result
import com.yeungkc.gank.io.model.repo.source.remote.GankRemoteSource
import com.yeungkc.gank.io.model.repo.source.remote.RequestBean
import com.yeungkc.gank.io.presenter.BasePresenter
import com.yeungkc.gank.io.view.PageView
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers


interface GankContract {
    interface GankView : PageView<List<Result>>

    class GankPresenter(val categorical: String) : BasePresenter<List<Result>, GankView>() {

//        val repo: GankRepo by lazy { GankRepo(categorical) }
        val r: GankRemoteSource by lazy { GankRemoteSource() }

        companion object {
            val DEFAULT_COUNT = 20
        }

        fun getContent() {
            getRemoteContent()
        }

        override var remoteSubscriber: BasePresenter.RemoteSubscriber<List<Result>>? = null

        @UiThread
        fun getRemoteContent(page: Int = 0, type: String = categorical, limit: Int = DEFAULT_COUNT) {
            cancelRemoteLoading()

            remoteSubscriber = object : BasePresenter.RemoteSubscriber<List<Result>>(v) {
                override fun onStart() {
                    v?.requestPage = page
                    super.onStart()
                }

                override fun onNext(t: List<Result>) {
                    v?.currentPage = page
                    v?.isNoData = t.size != limit

                    v?.setData(t)
                }
            }

            // api 起始頁碼是 1, 我的習慣是 0, 故此 +1
//            mRepo.getRemoteContent(RequestBean(type, page + 1, limit))
            r.requestContent(RequestBean(type, page + 1, limit))
                    .flatMap { Observable.from(it) }
                    .map {
                        if (it.type == "福利" && v != null) {
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
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(remoteSubscriber)
                    .pending(pendingSubscriptions)
        }

    }
}

