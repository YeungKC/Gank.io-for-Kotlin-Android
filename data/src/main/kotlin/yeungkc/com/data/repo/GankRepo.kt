package yeungkc.com.data.repo

import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import yeungkc.com.data.HttpResultFunc
import yeungkc.com.data.bean.Result
import yeungkc.com.data.service.GankService

/**
 * Created by YeungKC on 16/8/4.
 *
 * @项目名: Gank.io-for-Kotlin-Android
 * @包名: yeungkc.com.data.repo
 * @作者: YeungKC
 *
 * @描述：TODO
 */

object GankRepo {

    fun  categoricalData(categorical: String, page: Int, count: Int): Observable<List<Result>> {
      return  GankService.api.categoricalData(categorical, page, count)
                .map(HttpResultFunc<List<Result>>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(onNext, onError, onCompleted)
    }
}