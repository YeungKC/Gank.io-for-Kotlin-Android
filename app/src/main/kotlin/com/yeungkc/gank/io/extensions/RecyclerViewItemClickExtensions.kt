package com.yeungkc.gank.io.extensions

import android.support.v7.widget.RecyclerView
import android.view.View
import com.yeungkc.gank.io.R
import kotlin.properties.Delegates

var mOnItemClickListener: ((RecyclerView, Int, View) -> Unit)? = null
var mOnItemLongClickListener: ((RecyclerView, Int, View) -> Boolean)? = null
var mRecyclerView: RecyclerView by Delegates.notNull()

val mAttachListener: RecyclerView.OnChildAttachStateChangeListener = object : RecyclerView.OnChildAttachStateChangeListener {
    override fun onChildViewAttachedToWindow(view: View?) {

        view?.setOnClickListener { v ->
            val position = mRecyclerView.getChildViewHolder(v).adapterPosition
            mOnItemClickListener?.invoke(mRecyclerView, position, v)
        }

        view?.setOnLongClickListener { v ->
            val position = mRecyclerView.getChildViewHolder(v).adapterPosition
            return@setOnLongClickListener mOnItemLongClickListener?.invoke(mRecyclerView, position, v) ?: false
        }
    }

    override fun onChildViewDetachedFromWindow(p0: View?) {
    }
}

fun RecyclerView.setOnItemClickListener(onItemClickListener: (RecyclerView, Int, View) -> Unit) {
    mRecyclerView = this
    val tag = this.getTag(R.id.item_click_support)

    if (tag == null) {
        this.setTag (R.id.item_click_support, this)
        this.addOnChildAttachStateChangeListener(mAttachListener)
    }

    mOnItemClickListener = onItemClickListener
}


fun RecyclerView.setOnItemLongClickListener(onItemLongClickListener: (RecyclerView?, Int?, View) -> Boolean) {
    mRecyclerView = this
    val tag = this.getTag(R.id.item_click_support)

    if (tag == null) {
        this.setTag (R.id.item_click_support, this)
        this.addOnChildAttachStateChangeListener(mAttachListener)
    }

    mOnItemLongClickListener = onItemLongClickListener
}