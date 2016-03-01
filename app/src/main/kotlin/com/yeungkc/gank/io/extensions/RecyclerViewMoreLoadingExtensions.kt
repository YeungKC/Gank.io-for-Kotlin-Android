package com.yeungkc.gank.io.extensions

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager

/**
 * Created by YeungKC on 16/2/24.
 *
 * @项目名: kc
 * @包名: gank.io.kc.extensions
 * @作者: YeungKC
 *
 * @描述：TODO
 */
fun RecyclerView.moreLoading(more:(Int)->Unit){
    this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if(newState != RecyclerView.SCROLL_STATE_IDLE) return
            if (layoutManager !is StaggeredGridLayoutManager) return

            val maxPositions = (layoutManager as StaggeredGridLayoutManager).findLastVisibleItemPositions(null)
            val position = Math.max(maxPositions[0], maxPositions[1])

            if(position +1 == adapter.itemCount){
                more(position)
            }
        }
    })
}