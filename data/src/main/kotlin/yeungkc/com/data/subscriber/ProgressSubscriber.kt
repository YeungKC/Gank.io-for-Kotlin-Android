package yeungkc.com.data.subscriber

import rx.Subscriber

/**
 * Created by YeungKC on 16/8/4.
 *
 * @项目名: Gank.io-for-Kotlin-Android
 * @包名: yeungkc.com.data.subscriber
 * @作者: YeungKC
 *
 * @描述：TODO
 */
class ProgressSubscriber<T> (val mSubscriberOnNextListener: SubscriberOnNextListener<T>): Subscriber<T>() {

    override fun onStart() {

    }

    override fun onCompleted() {

    }

    override fun onError(e: Throwable?) {
    }

    override fun onNext(t: T) {
        mSubscriberOnNextListener.onNext(t)
    }
}