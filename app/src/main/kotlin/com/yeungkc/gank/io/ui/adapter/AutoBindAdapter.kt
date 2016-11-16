package com.yeungkc.gank.io.ui.adapter

import com.yeungkc.gank.io.ui.view_holder.BaseViewHolder

abstract class AutoBindAdapter<E>() : ArrayAdapter<E, BaseViewHolder<E>>() {
    override fun onBindViewHolder(holder: BaseViewHolder<E>, position: Int) {
        holder.bind(get(position))
    }
}