package com.yeungkc.gank.io.ui.adapter

import android.support.annotation.LayoutRes
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yeungkc.gank.io.R
import com.yeungkc.gank.io.model.bean.AutoBean
import com.yeungkc.gank.io.ui.view_holder.BaseViewHolder
import java.util.*

abstract class LoadingAdapter : AutoBindAdapter() {
    var onClickErrorItemListener: (() -> Unit)? = null

    companion object {
        const val LOADING_TYPE = -1
        const val ERROR_TYPE = LOADING_TYPE - 1
        const val NO_DATA_TYPE = ERROR_TYPE - 1

        const val LOADING_MORE_TYPE = NO_DATA_TYPE - 1
        const val LOADING_MORE_ERROR_TYPE = LOADING_MORE_TYPE - 1
        const val LOADING_MORE_NO_DATA_TYPE = LOADING_MORE_ERROR_TYPE - 1
    }

    var msgType = 0
    var loadMoreMsgType = 0

    override final fun getItemCount(): Int
            =
            if (isHaveDataSets()) {
                size + if (loadMoreMsgType == 0) 0 else 1
            } else {
                if (msgType == 0) 0 else 1
            }

    open fun getExItemCount(): Int = size

    override final fun getItemViewType(position: Int): Int {
        if (msgType != 0 && loadMoreMsgType != 0) {
            throw RuntimeException("msgType 和 loadMoreMsgType 不能同时不为 0")
        }

        return when {
            msgType != 0 -> msgType
            loadMoreMsgType != 0 && position == itemCount - 1 -> loadMoreMsgType
            else -> getExItemViewType(position)
        }
    }

    open fun getExItemViewType(position: Int): Int = 0

    override fun getItemId(position: Int): Long {
        return when {
            msgType != 0 -> msgType.toLong()
            loadMoreMsgType != 0 && position == itemCount - 1 -> loadMoreMsgType.toLong()
            else -> getExItemId(position)
        }
    }

    open fun getExItemId(position: Int): Long = super.getItemId(position)

    @Suppress("UNCHECKED_CAST")
    override final fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<AutoBean> {
        return when (viewType) {
            LOADING_TYPE -> LoadingViewHolder(parent, R.layout.item_loading)
            ERROR_TYPE -> ErrorViewHolder(parent, R.layout.item_error, onClickErrorItemListener)
            NO_DATA_TYPE -> NoDataViewHolder(parent, R.layout.item_no_data)
            LOADING_MORE_TYPE -> LoadingMoreViewHolder(parent, R.layout.item_loading_more)
            LOADING_MORE_ERROR_TYPE -> LoadingMoreErrorViewHolder(parent, R.layout.item_loading_more_error, onClickErrorItemListener)
            LOADING_MORE_NO_DATA_TYPE -> LoadingMoreNoDataViewHolder(parent, R.layout.item_loading_more_no_data)
            else -> onCreateExViewHolder(parent, viewType)
        }
    }

    abstract fun onCreateExViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<AutoBean>

    override final fun onBindViewHolder(holder: BaseViewHolder<AutoBean>, position: Int) {
        if (holder !is MsgViewHolder) onBindExViewHolder(holder, position)
    }

    open fun onBindExViewHolder(holder: BaseViewHolder<AutoBean>, position: Int) {
        super.onBindViewHolder(holder, position)
    }

    abstract class MsgViewHolder(view: View, event: (() -> Unit)? = null) : BaseViewHolder<kotlin.Any>(view) {
        init {
            event?.let { itemView.setOnClickListener { event() } }
            val layoutParams = itemView.layoutParams

            if (layoutParams is StaggeredGridLayoutManager.LayoutParams) {
                layoutParams.isFullSpan = true
            }
        }

        fun correctionHeight(parent: ViewGroup) {
            val layoutParams = itemView.layoutParams

            if (layoutParams.height != ViewGroup.LayoutParams.MATCH_PARENT) return

            var topMargin = 0
            var bottomMargin = 0

            if (layoutParams is ViewGroup.MarginLayoutParams) {
                topMargin = layoutParams.topMargin
                bottomMargin = layoutParams.bottomMargin
            }

            val height = parent.height
            val paddingTop = parent.paddingTop
            val paddingBottom = parent.paddingBottom

            layoutParams.height = height - paddingTop - paddingBottom - topMargin - bottomMargin

        }
    }

    class LoadingViewHolder(parent: ViewGroup, @LayoutRes layoutResID: Int, event: (() -> Unit)? = null) : MsgViewHolder(LayoutInflater.from(parent.context).inflate(layoutResID, parent, false), event) {
        init {
            correctionHeight(parent)
        }
    }

    class ErrorViewHolder(parent: ViewGroup, @LayoutRes layoutResID: Int, event: (() -> Unit)? = null) : MsgViewHolder(LayoutInflater.from(parent.context).inflate(layoutResID, parent, false), event) {
        init {
            correctionHeight(parent)
        }
    }

    class NoDataViewHolder(parent: ViewGroup, @LayoutRes layoutResID: Int, event: (() -> Unit)? = null) : MsgViewHolder(LayoutInflater.from(parent.context).inflate(layoutResID, parent, false), event) {
        init {
            correctionHeight(parent)
        }
    }

    class LoadingMoreViewHolder(parent: ViewGroup, @LayoutRes layoutResID: Int, event: (() -> Unit)? = null) : MsgViewHolder(LayoutInflater.from(parent.context).inflate(layoutResID, parent, false), event)
    class LoadingMoreErrorViewHolder(parent: ViewGroup, @LayoutRes layoutResID: Int, event: (() -> Unit)? = null) : MsgViewHolder(LayoutInflater.from(parent.context).inflate(layoutResID, parent, false), event)
    class LoadingMoreNoDataViewHolder(parent: ViewGroup, @LayoutRes layoutResID: Int, event: (() -> Unit)? = null) : MsgViewHolder(LayoutInflater.from(parent.context).inflate(layoutResID, parent, false), event)

    fun show(i: Int) {
        if (isHaveDataSets()) {
            replaceWith(ArrayList())
        }
        val isHaveMsg = msgType != 0
        val isSame = msgType == i

        msgType = i
        if (isHaveMsg) {
            if (!isSame) notifyItemChanged(0)
        } else {
            notifyItemInserted(0)
        }
    }

    fun showLoading() {
        show(LOADING_TYPE)
    }

    fun showError() {
        show(ERROR_TYPE)
    }

    fun showNoData() {
        show(NO_DATA_TYPE)
    }

    val loadMorePosition: Int
        get() {
            if (itemCount == size) throw RuntimeException("loadMoreMsg 为 0")
            return itemCount - 1
        }

    fun showLoadingMore(i: Int) {
        val isHaveLoadMoreMsg = loadMoreMsgType != 0
        val isSame = loadMoreMsgType == i

        loadMoreMsgType = i
        if (isHaveLoadMoreMsg) {
            if (!isSame)
                notifyItemChanged(loadMorePosition)
        } else {
            notifyItemInserted(loadMorePosition)
        }
    }

    fun showLoadMoreError() {
        showLoadingMore(LOADING_MORE_ERROR_TYPE)
    }

    fun showLoadMoreNoData() {
        showLoadingMore(LOADING_MORE_NO_DATA_TYPE)
    }

    fun showLoadingMore() {
        showLoadingMore(LOADING_MORE_TYPE)
    }

    fun hideLoadingMore() {
        if (loadMoreMsgType != 0) {
            val position = loadMorePosition
            loadMoreMsgType = 0
            notifyItemRemoved(position)
        }
    }

    override fun replaceWith(dataSets: List<AutoBean>,onLoaded:()->Unit) {
        if (dataSets.isNotEmpty() && msgType != 0) {
            msgType = 0
            notifyItemRemoved(0)
        }
        super.replaceWith(dataSets, onLoaded)
    }
}

