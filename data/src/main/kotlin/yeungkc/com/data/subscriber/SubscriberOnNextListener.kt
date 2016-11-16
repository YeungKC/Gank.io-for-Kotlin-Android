package yeungkc.com.data.subscriber

/**
 * Created by YeungKC on 16/8/4.
 *
 * @项目名: Gank.io-for-Kotlin-Android
 * @包名: yeungkc.com.data.subscriber
 * @作者: YeungKC
 *
 * @描述：TODO
 */
public interface SubscriberOnNextListener<T> {
    fun onNext(t: T)
}