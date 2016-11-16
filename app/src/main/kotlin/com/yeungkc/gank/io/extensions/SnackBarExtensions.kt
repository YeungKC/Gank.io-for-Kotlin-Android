package com.yeungkc.gank.io.extensions

import android.support.design.widget.Snackbar

fun Snackbar.show(paddingBottom: Int) {
    if (paddingBottom > 0) {
        view.setPadding(view.paddingLeft, view.paddingTop, view.paddingRight, view.paddingBottom + paddingBottom)
        view.requestLayout()
    }

    show()
}