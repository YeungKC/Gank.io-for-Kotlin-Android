package com.yeungkc.gank.io.extensions

import android.content.Context
import android.support.annotation.StringRes
import android.util.SparseArray

val sparseIntArray = SparseArray<String>()
fun Context.getResourcesString(@StringRes resId: Int): String {
    var string = sparseIntArray.get(resId, null)
    if (string == null) {
        string = getString(resId)
        sparseIntArray.put(resId, string)
    }

    return string
}

