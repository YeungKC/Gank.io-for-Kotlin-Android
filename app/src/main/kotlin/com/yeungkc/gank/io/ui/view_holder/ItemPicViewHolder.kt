package com.yeungkc.gank.io.ui.view_holder

import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.yeungkc.gank.io.R
import com.yeungkc.gank.io.model.bean.Result
import com.yeungkc.gank.io.ui.widget.RatioImageView
import org.jetbrains.anko.find

/**
 * @项目名: kc
 * @包名: gank.io.kc.ui.view_holder
 * @作者: YeungKC
 *
 * @描述：TODO
 */
class ItemPicViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val iv_item: RatioImageView by lazy { itemView.find<RatioImageView>(R.id.iv_item) }

    fun bind( results: Result) {
        with(results) {
            Glide.with(iv_item.context)
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(iv_item)

            if (width > 0 && height > 0) {
                iv_item.setOriginalSize(width, height)
            }
        }
    }
}
