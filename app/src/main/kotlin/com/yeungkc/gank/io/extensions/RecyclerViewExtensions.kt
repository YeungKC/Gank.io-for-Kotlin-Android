package com.yeungkc.gank.io.extensions

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import com.yeungkc.gank.io.ui.adapter.ArrayAdapter
import com.yeungkc.gank.io.ui.adapter.LoadingAdapter
import com.yeungkc.gank.io.ui.adapter.LoadingAdapter.Companion.LOADING_MORE_TYPE

fun RecyclerView.setOnLoadMore(more: (Int) -> Unit) {
    this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (dy < 0) return

            val lastVisibleItemPosition = recyclerView.getLastVisibleItemPosition()

            val itemCount = recyclerView.adapter.itemCount - 1

            if (itemCount != lastVisibleItemPosition) return

            if (recyclerView.getParentIsRefreshing()) return

            if (!recyclerView.getAdapterIsLoadMore()) return

            if (!recyclerView.getAdapterCanLoadMore()) return

            more(lastVisibleItemPosition)
        }
    })
}

fun RecyclerView.getAdapterCanLoadMore(): Boolean {
    var b = true
    val adapter = adapter
    if (adapter is ArrayAdapter<*, *>) {
        b = adapter.isCanLoading()
    }
    return b
}

fun RecyclerView.getAdapterIsLoadMore(): Boolean {
    var b = true

    val adapter = adapter
    if (adapter is LoadingAdapter) {
        b = adapter.loadMoreMsgType == LOADING_MORE_TYPE
    }
    return b
}

fun RecyclerView.getParentIsRefreshing(): Boolean {
    var isRefreshing = false
    val parent = parent

    if (parent is SwipeRefreshLayout) isRefreshing = parent.isRefreshing

    return isRefreshing
}

fun RecyclerView.getLastVisibleItemPosition(): Int {
    return layoutManager.getLastVisibleItemPosition()
}

fun RecyclerView.LayoutManager.getLastVisibleItemPosition(): Int {
    var lastVisibleItemPosition = -2

    when (this) {
        is LinearLayoutManager -> lastVisibleItemPosition = findLastVisibleItemPosition()

        is StaggeredGridLayoutManager -> {
            val findLastVisibleItemPositions = findLastVisibleItemPositions(null)

            for (i in findLastVisibleItemPositions)
                if (i >= lastVisibleItemPosition) lastVisibleItemPosition = i
        }
    }

    return lastVisibleItemPosition
}

fun RecyclerView.LayoutManager.getFirstVisibleItemPosition(): Int {
    var fristVisibleItemPosition = -2

    when (this) {
        is LinearLayoutManager -> fristVisibleItemPosition = findFirstVisibleItemPosition()

        is StaggeredGridLayoutManager -> {
            val findLastVisibleItemPositions = findFirstVisibleItemPositions(null)

            for (i in findLastVisibleItemPositions)
                if (i <= fristVisibleItemPosition) fristVisibleItemPosition = i
        }
    }

    return fristVisibleItemPosition
}

@JvmOverloads fun RecyclerView.onScrollShowHideAppBar(
        onSaveScrollOffset: (Int) -> Unit = {},
        hideToolBar: () -> Unit,
        showToolBar: () -> Unit
        ) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        var flag = false
            set(value) {
                if (field == value) return

                field = value
                if (field) {
                    hideToolBar()
                } else {
                    showToolBar()
                }
            }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (dy == 0) return

            val b = dy > 0
            val computeVerticalScrollOffset = recyclerView.computeVerticalScrollOffset()
            onSaveScrollOffset(computeVerticalScrollOffset)
            if (b) {
                val paddingTop = recyclerView.paddingTop
                if (computeVerticalScrollOffset < paddingTop) return
            }

            flag = b
        }
    })
}
