package com.yeungkc.gank.io.ui.view_holder

import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.yeungkc.gank.io.R
import com.yeungkc.gank.io.databinding.ItemGankPicBinding
import com.yeungkc.gank.io.model.bean.AutoBean
import com.yeungkc.gank.io.model.bean.Result
import com.yeungkc.gank.io.ui.activity.DetailActivity
import com.yeungkc.gank.io.ui.glide.module.CustomImageSizeModelGankStudio
import org.jetbrains.anko.startActivity
import java.text.SimpleDateFormat

class ItemPicViewHolder(val parent: ViewGroup) : BaseViewHolder<AutoBean>(LayoutInflater.from(parent.context).inflate(R.layout.item_gank_pic, parent, false)) {
    val bind:ItemGankPicBinding

    init {
     bind = ItemGankPicBinding.bind(itemView)
    }

    override fun bind(data: AutoBean) {
        if (data !is Result) return

        data.run {
            bind.flItem.setOriginalSize(width, height)

            if (url != itemView.tag) {
                itemView.tag = url
                Glide.with(bind.ivItem.context)
                        .load(CustomImageSizeModelGankStudio(url))
                        .placeholder(ColorDrawable(shotLoadingPlaceholderColor))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(bind.ivItem)
            }

            bind.tvItem.text = SimpleDateFormat("yy\nMM/dd").format(publishedAt)

            itemView.setOnClickListener {
                context.startActivity<DetailActivity>(DetailActivity.DATE to data.publishedAt)
            }
        }
    }
}

