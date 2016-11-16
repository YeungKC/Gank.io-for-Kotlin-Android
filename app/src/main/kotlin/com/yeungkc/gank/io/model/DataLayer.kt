package com.yeungkc.gank.io.model

import android.app.Application
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.yeungkc.gank.io.model.Interceptor.HttpLogInterceptor
import okhttp3.OkHttpClient

object DataLayer {

    private const val pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'"

    val gson: Gson by lazy {
        GsonBuilder()
                .setDateFormat(pattern)
//                .setExclusionStrategies(object : ExclusionStrategy {
//                    override fun shouldSkipClass(clazz: Class<*>?): Boolean = false
//
//                    override fun shouldSkipField(f: FieldAttributes): Boolean =
//                            f.declaringClass.equals(RealmObject::class.java)
//                })
//                .excludeFieldsWithoutExposeAnnotation()
                .create()
    }

    val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
                .addInterceptor(HttpLogInterceptor())
                .build()
    }

    fun hook(app: Application) {
//        Realm.setDefaultConfiguration(
//                RealmConfiguration.Builder(app)
//                        .deleteRealmIfMigrationNeeded()
//                        .build())
    }
}