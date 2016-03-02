package com.yeungkc.gank.io.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.yeungkc.gank.io.R
import com.yeungkc.gank.io.extensions.getNavigationBarHeight
import com.yeungkc.gank.io.model.bean.Result
import com.yeungkc.gank.io.ui.view_holder.ItemDetaitlViewHolder
import com.yeungkc.gank.io.ui.widget.ArrayRecyclerAdapter

/**
 * Created by YeungKC on 16/2/29.
 *
 * @项目名: kc
 * @包名: gank.io.kc.ui.adapter
 * @作者: YeungKC
 *
 * @描述：TODO
 */
class DetailAdapter(val mContext: Context) : ArrayRecyclerAdapter<Result, ItemDetaitlViewHolder>() {
    val mNavigationBarHeight:Int by lazy { mContext.getNavigationBarHeight() }
    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return get(position).url!!.hashCode().toLong()
    }

    override fun onBindViewHolder(holder: ItemDetaitlViewHolder, position: Int) {
        val isFirst = position == 0
        val isLast = position == itemCount - 1
        val isCategoryFirst = if (isFirst) true else !get(position - 1).type!!.equals(get(position).type)
        val isCategoryLast = if (isLast) true else !get(position + 1).type!!.equals(get(position).type)

        if (!isFirst || !isCategoryFirst || !isLast || !isCategoryLast) {
            holder.tvCategory.visibility = View.GONE
            holder.vTopDivider.visibility = View.GONE
        }
        if (isLast || isCategoryLast) {
            holder.tvCategory.visibility = View.GONE
            holder.vTopDivider.visibility = View.GONE
        }
        if (isFirst || isCategoryFirst) {
            holder.tvCategory.visibility = View.VISIBLE
            holder.vTopDivider.visibility = View.VISIBLE
        }

        // 设置透明 Navigation Bar 之后需要适当设置 margin, 不然内容就跟 Navigation Bar 重叠
        val layoutParams = holder.vBottomDivider.layoutParams as LinearLayout.LayoutParams
        if (isLast) {
            layoutParams.bottomMargin = mNavigationBarHeight
        } else {
            layoutParams.bottomMargin = 0
        }
        holder.vBottomDivider.layoutParams = layoutParams

        holder.bind(get(position), onItemClickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemDetaitlViewHolder? {
        return ItemDetaitlViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_detail, parent, false))
    }

    private var onItemClickListener: ((Result) -> Unit)? = null

    fun setOnItemClickListener(onItemClickListener: (Result) -> Unit) {
        this.onItemClickListener = onItemClickListener
    }
}
