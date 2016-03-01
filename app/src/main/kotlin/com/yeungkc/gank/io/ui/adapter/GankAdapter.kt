package com.yeungkc.gank.io.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.yeungkc.gank.io.R
import com.yeungkc.gank.io.model.bean.Result
import com.yeungkc.gank.io.ui.view_holder.ItemPicViewHolder
import com.yeungkc.gank.io.ui.widget.ArrayRecyclerAdapter


/**
 * Created by YeungKC on 16/2/22.
 *
 * @项目名: Kotlin Demo
 * @包名: kotlindemo.view.adapter
 * @作者: YeungKC
 *
 * @描述：TODO
 */
class GankAdapter() : ArrayRecyclerAdapter<Result, ItemPicViewHolder>() {
    init{
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemPicViewHolder? {
        return ItemPicViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_pic, parent, false))
    }

    override fun getItemId(position: Int): Long {
        return get(position).url!!.hashCode().toLong()
    }

    override fun onBindViewHolder(viewHolder: ItemPicViewHolder, position: Int) {
        viewHolder.bind(get(position))
    }
}

