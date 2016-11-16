package com.yeungkc.gank.io.ui.glide.module

import android.content.Context
import com.bumptech.glide.load.model.stream.BaseGlideUrlLoader

class CustomImageSizeUrlLoader(context: Context) :BaseGlideUrlLoader<CustomImageSizeModel>(context){
    override fun getUrl(model: CustomImageSizeModel, width: Int, height: Int): String? {
        return model.requestCustomSizeUrl( width, height )
    }
}