package com.yeungkc.gank.io.extensions

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.support.annotation.DrawableRes
import android.support.v7.widget.AppCompatDrawableManager

fun Context.getBitmap(@DrawableRes resVector:Int):Bitmap{
    val drawable = AppCompatDrawableManager.get().getDrawable(this, resVector)
    val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    drawable.setBounds(0,0,canvas.width,canvas.height)
    drawable.draw(canvas)

    return bitmap
}