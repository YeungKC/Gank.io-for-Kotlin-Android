package com.yeungkc.gank.io.extensions

import android.content.Context

/**
 * Created by YeungKC on 16/3/2.
 *
 * @项目名: Gank.io-for-Kotlin-Android
 * @包名: com.yeungkc.gank.io.extensions
 * @作者: YeungKC
 *
 * @描述：TODO
 */
fun Int.pxToDp(context: Context): Int {
    return (this / context.resources.displayMetrics.density + 0.5f ).toInt()
}

fun Int.dpToPx(context: Context): Int {
    return (this * context.resources.displayMetrics.density + 0.5f ).toInt()
}

