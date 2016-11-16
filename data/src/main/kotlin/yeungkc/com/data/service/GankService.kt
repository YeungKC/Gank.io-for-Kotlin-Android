package yeungkc.com.data.service

import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable
import yeungkc.com.data.BuildConfig
import yeungkc.com.data.DataLayer
import yeungkc.com.data.bean.BaseResult
import yeungkc.com.data.bean.DayResult
import yeungkc.com.data.bean.Result

/**
 * Created by YeungKC on 16/8/4.
 *
 * @项目名: Gank.io-for-Kotlin-Android
 * @包名: yeungkc.com.data.service
 * @作者: YeungKC
 *
 * @描述：TODO
 */

class GankService {
    companion object {
        val api: APIs

        init {
            val restAdapter = Retrofit.Builder()
                    .baseUrl(BuildConfig.API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(DataLayer.gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(DataLayer.okHttpClient)
                    .build()

            api = restAdapter.create(APIs::class.java)
        }
    }

    interface APIs {
        @GET("data/{categorical}/{count}/{page}")
        fun categoricalData(
                @Path("categorical") categorical: String,
                @Path("page") page: Int,
                @Path("count") count: Int): Observable<BaseResult<List<Result>>>


        @GET("day/{year}/{month}/{day}")
        fun getDetailData(
                @Path("year") year: String,
                @Path("month") month: String,
                @Path("day") day: String): Observable<BaseResult<DayResult>>
    }
}

