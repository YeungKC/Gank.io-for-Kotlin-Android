package com.yeungkc.gank.io.ui.view_holder

import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import com.yeungkc.gank.io.R
import com.yeungkc.gank.io.model.bean.Result
import org.jetbrains.anko.find

/**
 * Created by YeungKC on 16/2/29.
 *
 * @项目名: kc
 * @包名: gank.io.kc.ui.view_holder
 * @作者: YeungKC
 *
 * @描述：TODO
 */
class ItemDetaitlViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val tvCategory: AppCompatTextView by lazy { itemView.find<AppCompatTextView>(R.id.tv_item_category) }
    val llContainer: LinearLayout by lazy { itemView.find<LinearLayout>(R.id.ll_item_container) }
    val tvTitle: AppCompatTextView by lazy { itemView.find<AppCompatTextView>(R.id.tv_item_title) }
    val tvWho: AppCompatTextView by lazy { itemView.find<AppCompatTextView>(R.id.tv_item_who) }
    val vTopDivider: View by lazy { itemView.find<View>(R.id.v_item_top_divider) }
    val vBottomDivider: View by lazy { itemView.find<View>(R.id.v_item_bottom_divider) }

    fun bind(result: Result, onClickListener: ((Result) -> Unit)?) {
        with(result) {
            tvCategory.text = type
            tvTitle.text = desc
            tvWho.text = itemView.resources.getString(R.string.via, who)
            llContainer.setOnClickListener { onClickListener?.invoke(this) }
        }

    }
}
