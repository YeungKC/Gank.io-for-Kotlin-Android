package com.yeungkc.gank.io.extensions

import rx.Subscription
import rx.subscriptions.CompositeSubscription

fun Subscription.pending(compositeSubscription: CompositeSubscription) {
    compositeSubscription.add(this)
}
