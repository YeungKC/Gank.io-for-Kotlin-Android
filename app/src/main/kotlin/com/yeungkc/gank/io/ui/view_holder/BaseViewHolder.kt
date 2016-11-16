package com.yeungkc.gank.io.ui.view_holder

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View

abstract class BaseViewHolder<in T>(view: View) : RecyclerView.ViewHolder(view) {
    val context: Context
        get() = itemView.context

    open fun bind(data: T) {

    }

    open fun bind() {

    }
}

