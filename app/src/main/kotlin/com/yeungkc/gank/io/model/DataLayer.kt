package com.yeungkc.gank.io.model

import android.app.Application
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import java.text.SimpleDateFormat
import java.util.*

object DataLayer {

    private const val PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'"

    val gson: Gson by lazy {
        GsonBuilder()
                .setDateFormat(PATTERN)
//                .excludeFieldsWithoutExposeAnnotation()
                .create()
    }

    val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
                .addNetworkInterceptor(StethoInterceptor())
                .build()
    }

    private const val SIMPLE_PATTERN = "yyyy-MM-dd"

    val simpleDateFormat = SimpleDateFormat(SIMPLE_PATTERN, Locale.getDefault())

    private const val PIC_PATTERN = "yy\nMM/dd"

    val picDateFormat = SimpleDateFormat(PIC_PATTERN, Locale.getDefault())

    fun hook(app: Application) {
    }
}