package com.yeungkc.gank.io.contract

import com.imallan.gankmvp.extensions.pending
import com.yeungkc.gank.io.model.bean.AutoBean
import com.yeungkc.gank.io.model.bean.Result
import com.yeungkc.gank.io.model.bean.Subtitle
import com.yeungkc.gank.io.model.repo.source.remote.DetailRemoteSource
import com.yeungkc.gank.io.presenter.BasePresenter
import com.yeungkc.gank.io.view.BaseView
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Func1
import java.util.*

interface DetailContract {
    interface DetailView : BaseView<List<AutoBean>>

    class DetailPresenter(val date: Date?) : BasePresenter<List<AutoBean>, DetailView>() {
        val remoteSource by lazy { DetailRemoteSource() }

        override var remoteSubscriber: BasePresenter.RemoteSubscriber<List<AutoBean>>? = null

        fun getContent() {
            cancelRemoteLoading()
            remoteSubscriber = object : RemoteSubscriber<List<AutoBean>>(v) {
                override fun onNext(t: List<AutoBean>) {
                    v?.setData(t)
                }
            }

            val observable: Observable<List<Result>>

            if (date != null) {
                observable = remoteSource.requestContent(date)
            } else {
                observable = remoteSource.getHistory()
                        .flatMap { remoteSource.requestContent(it[0]) }
            }

            observable
                    .map(TransFormDataSets())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(remoteSubscriber)
                    .pending(pendingSubscriptions)
        }
    }

    class TransFormDataSets : Func1<List<Result>, ArrayList<AutoBean>> {
        override fun call(t: List<Result>): ArrayList<AutoBean> {
            val arrayList: ArrayList<AutoBean> = ArrayList<AutoBean>()
            var tempType: String? = null
            for (result in t) {
                val type = result.type
                if (tempType != type) {
                    tempType = type
                    arrayList.add(Subtitle(type))
                }
                arrayList.add(result)
            }
            return arrayList
        }
    }
}