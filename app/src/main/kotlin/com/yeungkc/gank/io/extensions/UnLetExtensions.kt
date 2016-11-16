package com.yeungkc.gank.io.extensions

fun<T> T?.unLet(block: (T?) -> Unit) {
    if (this == null) block(this)
}