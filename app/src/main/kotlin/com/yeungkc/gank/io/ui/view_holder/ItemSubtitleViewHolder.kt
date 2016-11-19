package com.yeungkc.gank.io.ui.view_holder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yeungkc.gank.io.R
import com.yeungkc.gank.io.databinding.ItemSubtitleBinding
import com.yeungkc.gank.io.model.bean.AutoBean
import com.yeungkc.gank.io.model.bean.Subtitle
import com.yeungkc.gank.io.ui.activity.MainActivity


class ItemSubtitleViewHolder(parent: ViewGroup) : BaseViewHolder<AutoBean>(LayoutInflater.from(parent.context).inflate(R.layout.item_subtitle, parent, false)) {
    val bind: ItemSubtitleBinding

    init {
        bind = ItemSubtitleBinding.bind(itemView)
    }

    override fun bind(data: AutoBean) {
        if (data !is Subtitle) return
        bind.icon = context.resources.getDrawable(data.getIcon(context))
        bind.subtitle = data.subTitle
        val mainActivity = context
        if (mainActivity is MainActivity) {
            bind.btMore.setOnClickListener {
                mainActivity.showFragment(data.subTitle)
            }
            bind.btMore.visibility = View.VISIBLE
        } else {
            bind.btMore.visibility = View.GONE
        }
        bind.executePendingBindings()
    }
}