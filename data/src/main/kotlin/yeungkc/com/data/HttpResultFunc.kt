package yeungkc.com.data

import rx.functions.Func1
import yeungkc.com.data.bean.BaseResult

/**
 * Created by YeungKC on 16/8/4.
 *
 * @项目名: Gank.io-for-Kotlin-Android
 * @包名: yeungkc.com.data
 * @作者: YeungKC
 *
 * @描述：TODO
 */
class HttpResultFunc<T> : Func1<BaseResult<T>, T> {
    override fun call(t: BaseResult<T>): T {
        if (t.error) throw ApiException(t.error)
        return t.results
    }
}