package com.yeungkc.gank.io.extensions

import android.content.Context
import android.content.res.Configuration
import android.view.KeyCharacterMap
import android.view.KeyEvent
import android.view.ViewConfiguration

/**
 * Created by YeungKC on 16/3/2.
 *
 * @项目名: Gank.io-for-Kotlin-Android
 * @包名: com.yeungkc.gank.io.extensions
 * @作者: YeungKC
 *
 * @描述：TODO
 */
fun Context.getNavigationBarHeight():Int {
    var result = 0;
    var hasMenuKey = ViewConfiguration.get(this).hasPermanentMenuKey();
    var hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);

    if (!hasMenuKey && !hasBackKey) {
        //The device has a navigation bar
//        var resources = this.resources;

        var orientation = resources.configuration.orientation;
        var resourceId: Int;
        if (isTablet()) {
            resourceId = resources.getIdentifier(if (orientation == Configuration.ORIENTATION_PORTRAIT ) "navigation_bar_height" else "navigation_bar_height_landscape", "dimen", "android");
        } else {
            resourceId = resources.getIdentifier(if(orientation == Configuration.ORIENTATION_PORTRAIT)  "navigation_bar_height" else "navigation_bar_width", "dimen", "android");
        }

        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
    }
    return result;
}

fun Context.isTablet(): Boolean {
    return (resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK) >=
            Configuration.SCREENLAYOUT_SIZE_LARGE
}