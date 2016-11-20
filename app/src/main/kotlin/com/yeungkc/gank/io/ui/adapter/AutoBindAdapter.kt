package com.yeungkc.gank.io.ui.adapter

import com.yeungkc.gank.io.model.bean.AutoBean
import com.yeungkc.gank.io.ui.view_holder.BaseViewHolder

abstract class AutoBindAdapter() : ArrayAdapter<AutoBean, BaseViewHolder<AutoBean>>() {
    override fun onBindViewHolder(holder: BaseViewHolder<AutoBean>, position: Int) {
        holder.bind(get(position))
    }
}