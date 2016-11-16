package com.yeungkc.gank.io.ui.glide.module

class CustomImageSizeModelGankStudio(var baseImageUrl: String?) : CustomImageSizeModel {
    override fun requestCustomSizeUrl(width: Int, height: Int): String? {
        if (baseImageUrl?.contains("img.gank.io") ?: false) {
            baseImageUrl = "$baseImageUrl?imageView2/0/w/$width/h/$height"
        }

        return baseImageUrl
    }
}