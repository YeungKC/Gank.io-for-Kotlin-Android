package com.yeungkc.gank.io.extensions

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.util.TypedValue
import android.view.WindowManager


fun Context.isOrientationPortrait(): Boolean {
    return resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
}

fun Context.isTranslucentNavigation(): Boolean {
    if (this !is Activity) {
        return false
    }
    val windowFlags = this.window.attributes.flags
    return windowFlags and WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION == WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
}

fun Context.getToolBarHeight(): Int {
    val value = TypedValue()
    theme.resolveAttribute(android.R.attr.actionBarSize, value, true)
    return TypedValue.complexToDimensionPixelSize(value.data, this.resources.displayMetrics)
}