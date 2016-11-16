package yeungkc.com.data

import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.realm.RealmObject
import okhttp3.OkHttpClient
import yeungkc.com.data.Interceptor.HttpLogInterceptor

/**
 * Created by YeungKC on 16/8/4.
 *
 * @项目名: Gank.io-for-Kotlin-Android
 * @包名: yeungkc.com.data.service
 * @作者: YeungKC
 *
 * @描述：TODO
 */
object DataLayer {
    private val pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    val gson: Gson = GsonBuilder()
                    .setDateFormat(pattern)
                    .setExclusionStrategies(object : ExclusionStrategy {
                        override fun shouldSkipClass(clazz: Class<*>?): Boolean = false

                        override fun shouldSkipField(f: FieldAttributes): Boolean =
                                f.declaringClass.equals(RealmObject::class.java)
                    })
                    .create()
    val okHttpClient:OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLogInterceptor())
            .build()

}