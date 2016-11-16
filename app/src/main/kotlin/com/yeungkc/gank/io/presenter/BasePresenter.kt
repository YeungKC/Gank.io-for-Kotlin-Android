package com.yeungkc.gank.io.presenter

import com.orhanobut.logger.Logger
import com.yeungkc.gank.io.view.BaseView
import rx.Subscriber
import rx.subscriptions.CompositeSubscription

abstract class BasePresenter<T,V : BaseView<*>>() {
    var v: V? = null

    val pendingSubscriptions = CompositeSubscription()

    open fun bind(v: V) {
        this.v = v
    }

    open fun unBind(isFinishing: Boolean) {
        v = null
        if (isFinishing) pendingSubscriptions.clear()
    }

   open var mRemoteSubscriber: RemoteSubscriber<T>? = null

    open fun isRemoteLoading(): Boolean = !(mRemoteSubscriber?.isUnsubscribed ?: true)

    open fun cancelRemoteLoading() = mRemoteSubscriber?.unsubscribe()

    abstract class RemoteSubscriber<T>(val view: BaseView<*>?) : Subscriber<T>() {
        override fun onStart() {
            view?.onLoading()
        }

        override fun onCompleted() {
        }

        override fun onError(e: Throwable) {
            view?.onError(e)
            Logger.e(e,"")
        }
    }
}