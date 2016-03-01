package com.yeungkc.gank.io.model.server

import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.yeungkc.gank.io.conf.API_BASE_URL
import com.yeungkc.gank.io.extensions.customExecute
import com.yeungkc.gank.io.extensions.day
import com.yeungkc.gank.io.extensions.month
import com.yeungkc.gank.io.extensions.year
import com.yeungkc.gank.io.model.BaseRequestCallBack
import com.yeungkc.gank.io.model.bean.BaseResult
import com.yeungkc.gank.io.model.bean.DayResult
import com.yeungkc.gank.io.model.bean.Result
import com.yeungkc.gank.io.model.server.IGankServer
import io.realm.RealmObject
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

/**
 * Created by YeungKC on 16/2/24.
 *
 * @项目名: kc
 * @包名: gank.io.kc.model
 * @作者: YeungKC
 *
 * @描述：网络封装类
 */
class GankServer private constructor() {
    val client by lazy { OkHttpClient() }
    val gson: Gson by lazy {
        //        val s = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
        val pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'"
        GsonBuilder()
                .setDateFormat(pattern)
                .setExclusionStrategies(object : ExclusionStrategy {
                    override fun shouldSkipClass(clazz: Class<*>?): Boolean = false

                    override fun shouldSkipField(f: FieldAttributes): Boolean =
                            f.declaringClass.equals(RealmObject::class.java)
                })
                .create()
    }
    val converterFactory by lazy { GsonConverterFactory.create(gson) }
    val retrofit by lazy {
        Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .client(client)
                .addConverterFactory(converterFactory)
                .build()
    }
    val service: IGankServer by lazy { retrofit.create(IGankServer::class.java) }

    companion object {
        val instance: GankServer by lazy { GankServer() }
    }


    fun getFuli(page: Int, count: Int, requestCallBack: BaseRequestCallBack<BaseResult<List<Result>>>,
                categorical: String = "福利")
            = service.categoricalData(categorical, page, count).customExecute(requestCallBack)

    fun getDetail(publishedAt: Date, requestCallBack: GankRequestCallBack<BaseResult<DayResult>>) {
        service.getDetailData(publishedAt.year(), publishedAt.month(), publishedAt.day())
                .customExecute(requestCallBack)
    }


}