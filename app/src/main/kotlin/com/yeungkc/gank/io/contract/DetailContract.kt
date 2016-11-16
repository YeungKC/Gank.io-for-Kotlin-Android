package com.yeungkc.gank.io.contract

import com.imallan.gankmvp.extensions.pending
import com.yeungkc.gank.io.model.bean.AutoBean
import com.yeungkc.gank.io.model.bean.Subtitle
import com.yeungkc.gank.io.model.repo.source.remote.DetailRemoteSource
import com.yeungkc.gank.io.presenter.BasePresenter
import com.yeungkc.gank.io.view.BaseView
import rx.android.schedulers.AndroidSchedulers
import java.util.*

interface DetailContract {
    interface DetailView : BaseView<List<AutoBean>>

    class DetailPresenter : BasePresenter<List<AutoBean>, DetailView>() {
        val remoteSource by lazy { DetailRemoteSource() }

        override var mRemoteSubscriber: BasePresenter.RemoteSubscriber<List<AutoBean>>? = null

        fun getContent() {
            cancelRemoteLoading()

            mRemoteSubscriber = object : BasePresenter.RemoteSubscriber<List<AutoBean>>(v) {
                override fun onNext(t: List<AutoBean>) {
                    v?.setData(t)
                }
            }
            remoteSource.requestContent(true)
                    .flatMap {
                        val arrayList = ArrayList<AutoBean>()
                        var tempType: String? = null
                        for (result in it) {
                            val type = result.type
                            if (tempType != type) {
                                tempType = type
                                arrayList.add(Subtitle(type))
                            }
                            arrayList.add(result)
                        }

                        rx.Observable.just(arrayList)
                    }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(mRemoteSubscriber)
                    .pending(pendingSubscriptions)
        }
    }
}