package com.yeungkc.gank.io.ui.view_holder

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import com.yeungkc.gank.io.R
import com.yeungkc.gank.io.databinding.ItemSubtitleBinding
import com.yeungkc.gank.io.model.bean.AutoBean
import com.yeungkc.gank.io.model.bean.Subtitle


class ItemSubtitleViewHolder(parent: ViewGroup) : BaseViewHolder<AutoBean>(LayoutInflater.from(parent.context).inflate(R.layout.item_subtitle, parent, false)) {
    val bind: ItemSubtitleBinding

    init {
        bind = ItemSubtitleBinding.bind(itemView)
    }

    override fun bind(data: AutoBean) {
        if (data !is Subtitle) return
        val icon: Drawable = context.resources.getDrawable(data.getIcon(context))
        bind.icon = icon
        bind.subtitle = data.subTitle
        bind.executePendingBindings()
    }
}