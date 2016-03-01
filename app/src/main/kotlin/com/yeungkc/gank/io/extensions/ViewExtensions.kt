package com.yeungkc.gank.io.extensions

import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout

/**
 * Created by YeungKC on 16/2/26.
 *
 * @项目名: kc
 * @包名: gank.io.kc.extensions
 * @作者: YeungKC
 *
 * @描述：TODO
 */
fun View.setSize(width: Int, height: Int) {
    if (width <= 0 && height <= 0) return

    val layoutParams = when (this.layoutParams) {
        is LinearLayout.LayoutParams -> this.layoutParams as LinearLayout.LayoutParams
        is FrameLayout.LayoutParams -> this.layoutParams as FrameLayout.LayoutParams
        is RelativeLayout.LayoutParams -> this.layoutParams as RelativeLayout.LayoutParams
        else ->  this.layoutParams
    }

    val ratio = height / width

    layoutParams.height = layoutParams.width * ratio

    this.layoutParams = layoutParams
}
