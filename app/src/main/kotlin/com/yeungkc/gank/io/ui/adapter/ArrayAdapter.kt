package com.yeungkc.gank.io.ui.adapter

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import com.yeungkc.gank.io.DiffCallback
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*

abstract class ArrayAdapter<E, VH : RecyclerView.ViewHolder>(capacity: Int = 0) : RecyclerView.Adapter<VH>() {
    var dataSets: List<E>

    init {
        dataSets = ArrayList<E>(capacity)
    }

    override fun getItemCount(): Int {
        return size
    }

    val size: Int
        get() = dataSets.size

    fun get(index: Int): E {
        return dataSets[index]
    }

    var oldSize = size
    fun isCanLoading() = size == oldSize

    open fun replaceWith(dataSets: List<E>, onLoaded: () -> Unit = {}) {
        if (!isCanLoading()) throw RuntimeException("now adapter can't loading")

        val newDataSets = dataSets
        val oldDataSets = this.dataSets

        this.dataSets = newDataSets

        if (newDataSets.isEmpty() || oldDataSets.isEmpty()) {
            DiffUtil.calculateDiff(DiffCallback(newDataSets, oldDataSets)).dispatchUpdatesTo(this)
            oldSize = size
            onLoaded()
        } else {
            Observable.create<DiffUtil.DiffResult> {
                it.onStart()
                it.onNext(DiffUtil.calculateDiff(DiffCallback(newDataSets, oldDataSets)))
                it.onCompleted()
            }
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        it.dispatchUpdatesTo(this)
                        oldSize = size
                        onLoaded()
                    }
        }
    }

    open fun addDataSets(dataSets: List<E>,onLoaded: () -> Unit) {
        replaceWith(this.dataSets + dataSets, onLoaded)
    }

    fun isHaveDataSets(): Boolean = dataSets.isNotEmpty()
}