package com.yeungkc.gank.io.ui.adapter

import android.view.ViewGroup
import com.yeungkc.gank.io.model.bean.AutoBean
import com.yeungkc.gank.io.model.bean.Result
import com.yeungkc.gank.io.model.bean.Result.Companion.GANK_PIC_TYPE
import com.yeungkc.gank.io.model.bean.Result.Companion.GANK_TYPE
import com.yeungkc.gank.io.model.bean.Subtitle.Companion.GANK_SUBTITLE_TYPE
import com.yeungkc.gank.io.ui.view_holder.BaseViewHolder
import com.yeungkc.gank.io.ui.view_holder.ItemGankViewHolder
import com.yeungkc.gank.io.ui.view_holder.ItemPicViewHolder
import com.yeungkc.gank.io.ui.view_holder.ItemSubtitleViewHolder


class GankAdapter(placeholderColors: IntArray = IntArray(0)) : LoadingAdapter() {
    val shotLoadingPlaceholderColors: IntArray

    init {
        setHasStableIds(true)

        shotLoadingPlaceholderColors = placeholderColors
    }

    override fun onCreateExViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<AutoBean> {
        when (viewType) {
            GANK_SUBTITLE_TYPE -> return ItemSubtitleViewHolder(parent)
            GANK_PIC_TYPE -> return ItemPicViewHolder(parent)
            else -> return ItemGankViewHolder(parent)
        }
    }

    override fun onBindExViewHolder(holder: BaseViewHolder<AutoBean>, position: Int) {
        val get = get(position)
        if (get is Result && shotLoadingPlaceholderColors.isNotEmpty())
            get.shotLoadingPlaceholderColor = shotLoadingPlaceholderColors[position % shotLoadingPlaceholderColors.size]
        super.onBindExViewHolder(holder, position)
    }

    override fun getExItemViewType(position: Int): Int {
        return when (get(position).itemType) {
            GANK_SUBTITLE_TYPE -> GANK_SUBTITLE_TYPE
            GANK_PIC_TYPE -> GANK_PIC_TYPE
            else -> GANK_TYPE
        }
    }

    override fun getExItemId(position: Int): Long {
        return get(position).itemId
    }
}

