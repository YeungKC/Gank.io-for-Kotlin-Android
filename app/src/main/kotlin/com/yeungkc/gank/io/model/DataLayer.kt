package com.yeungkc.gank.io.model

import android.app.Application
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient

object DataLayer {

    private const val pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'"

    val gson: Gson by lazy {
        GsonBuilder()
                .setDateFormat(pattern)
//                .excludeFieldsWithoutExposeAnnotation()
                .create()
    }

    val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
                .addNetworkInterceptor(StethoInterceptor())
                .build()
    }

    fun hook(app: Application) {
    }
}