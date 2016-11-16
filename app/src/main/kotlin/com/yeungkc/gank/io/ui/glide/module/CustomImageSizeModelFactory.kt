package com.yeungkc.gank.io.ui.glide.module

import android.content.Context
import com.bumptech.glide.load.model.GenericLoaderFactory
import com.bumptech.glide.load.model.ModelLoader
import com.bumptech.glide.load.model.ModelLoaderFactory
import java.io.InputStream

class CustomImageSizeModelFactory : ModelLoaderFactory<CustomImageSizeModel, InputStream> {
    override fun teardown() {
    }

    override fun build(context: Context, factories: GenericLoaderFactory?): ModelLoader<CustomImageSizeModel, InputStream> {
        return CustomImageSizeUrlLoader(context)
    }
}