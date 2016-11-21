package com.yeungkc.gank.io.ui.view_holder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yeungkc.gank.io.R
import com.yeungkc.gank.io.databinding.ItemSubtitleBinding
import com.yeungkc.gank.io.model.bean.AutoBean
import com.yeungkc.gank.io.model.bean.Subtitle
import com.yeungkc.gank.io.ui.activity.MainActivity
import org.greenrobot.eventbus.EventBus


class ItemSubtitleViewHolder(parent: ViewGroup) : BaseViewHolder<AutoBean>(LayoutInflater.from(parent.context).inflate(R.layout.item_subtitle, parent, false)) {
    var data: Subtitle? = null
    val bind: ItemSubtitleBinding

    init {
        bind = ItemSubtitleBinding.bind(itemView)

        bind.btMore.setOnClickListener {
            data?.run {
                EventBus.getDefault().post(subTitle)
            }
        }
    }

    override fun bind(data: AutoBean) {
        if (data !is Subtitle) return

        this.data = data

        bind.icon = context.resources.getDrawable(data.getIcon(context))
        bind.subtitle = data.subTitle
        val mainActivity = context
        bind.btMore.visibility = if (mainActivity is MainActivity) View.VISIBLE else View.GONE

        bind.executePendingBindings()
    }
}