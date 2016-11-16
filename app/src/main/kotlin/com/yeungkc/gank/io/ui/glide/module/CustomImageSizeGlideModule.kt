package com.yeungkc.gank.io.ui.glide.module

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.module.GlideModule
import java.io.InputStream

class CustomImageSizeGlideModule : GlideModule {
    override fun registerComponents(context: Context?, glide: Glide) {
        glide.register(CustomImageSizeModel::class.java, InputStream::class.java, CustomImageSizeModelFactory())
    }

    override fun applyOptions(context: Context?, builder: GlideBuilder) {
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888)
    }
}