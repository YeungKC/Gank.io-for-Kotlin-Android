package com.yeungkc.gank.io

import android.app.Application
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import io.realm.Realm
import io.realm.RealmConfiguration
import okhttp3.OkHttpClient
import java.io.InputStream

/**
 * @项目名: kc
 * @包名: gank.io.kc
 * @作者: YeungKC
 *
 * @描述：TODO
 */
class CustomApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Realm.setDefaultConfiguration(
                RealmConfiguration.Builder(this)
                        .deleteRealmIfMigrationNeeded()
                        .build())

        Glide.get(this)
                .register(GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory(OkHttpClient()))
    }
}