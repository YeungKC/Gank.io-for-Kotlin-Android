package com.yeungkc.gank.io

import android.support.v7.util.DiffUtil

class DiffCallback<E>(val newDataSets:List<E>?, val oldDataSets:List<E>?) : DiffUtil.Callback(){
    override fun getOldListSize(): Int = oldDataSets?.size ?: 0

    override fun getNewListSize(): Int = newDataSets?.size ?: 0

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = oldDataSets?.get(oldItemPosition) == newDataSets?.get(newItemPosition)

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = oldDataSets?.get(oldItemPosition) == newDataSets?.get(newItemPosition)
}