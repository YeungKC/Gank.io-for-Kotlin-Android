package com.yeungkc.gank.io.model.service

import com.yeungkc.gank.io.BuildConfig
import com.yeungkc.gank.io.model.DataLayer
import com.yeungkc.gank.io.model.bean.BaseResult
import com.yeungkc.gank.io.model.bean.DayResult
import com.yeungkc.gank.io.model.bean.Result
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable
import java.util.*

public class GankService {
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

        @GET("day/history")
        fun getHistory(): Observable<BaseResult<List<Date>>>
    }
}

