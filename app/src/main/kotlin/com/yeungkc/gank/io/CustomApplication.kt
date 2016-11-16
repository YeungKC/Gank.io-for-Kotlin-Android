package com.yeungkc.gank.io

import android.app.Application
import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.yeungkc.gank.io.model.DataLayer
import okhttp3.OkHttpClient
import java.io.InputStream

class CustomApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        contenxt = this

        DataLayer.hook(this)

        initGlide()

        initLogger()
    }

    companion object{
        lateinit var contenxt:Context
    }

    private fun initLogger() {
//        Logger
//                .init()
//                .methodCount(3)
//                .logLevel(if (BuildConfig.DEBUG) LogLevel.NONE else LogLevel.FULL)
//                .methodOffset(2)
    }

    private fun initGlide() {
        Glide.get(this)
                .register(GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory(OkHttpClient()))
    }
}