package com.yeungkc.gank.io.ui.widget

import android.support.v7.widget.RecyclerView
import java.util.*

abstract class ArrayRecyclerAdapter<E, VH : RecyclerView.ViewHolder>() : RecyclerView.Adapter<VH>(), MutableList<E> {
    val lock: Any by lazy { Any() }
    lateinit var list: MutableList<E>

    init {
        list = ArrayList<E>()
    }

    constructor(capacity: Int) : this() {
        list = ArrayList<E>(capacity)
    }

    override fun getItemCount(): Int {
        return size
    }

    override val size: Int
        get() = list.size

    override fun add(index: Int, element: E) {
        synchronized (lock) {
            list.add(index, element)
            if (index == 0) notifyDataSetChanged() else notifyItemInserted(index)
        }
    }

    override fun add(element: E): Boolean {
        synchronized (lock) {
            val lastIndex = list.size
            if (list.add(element)) {
                if (lastIndex == 0) notifyDataSetChanged() else notifyItemInserted(lastIndex)
                return true
            } else {
                return false
            }
        }
    }


    override fun addAll(index: Int, elements: Collection<E>): Boolean {
        synchronized (lock) {
            if (list.addAll(index, elements)) {
                if (index == 0) notifyDataSetChanged() else notifyItemRangeInserted(index, elements.size)
                return true
            } else {
                return false
            }
        }
    }

    override fun addAll(elements: Collection<E>): Boolean {
        synchronized (lock) {
            val lastIndex = list.size
            if (list.addAll(elements)) {
                if (lastIndex == 0) notifyDataSetChanged() else notifyItemRangeInserted(lastIndex, elements.size)
                return true
            } else {
                return false
            }
        }
    }

    override fun clear() {
        synchronized (lock) {
            val size = list.size
            if (size > 0) {
                list.clear()
                notifyItemRangeRemoved(0, size)
            }
        }
    }

    override fun contains(element: E): Boolean {
        return list.contains(element)
    }

    override fun containsAll(elements: Collection<E>): Boolean {
        return list.containsAll(elements)
    }

    override fun get(index: Int): E {
        return list[index]
    }

    override fun indexOf(element: E): Int {
        return list.indexOf(element)
    }

    override fun isEmpty(): Boolean {
        return list.isEmpty()
    }

    override fun iterator(): MutableIterator<E> {
        return list.iterator()
    }

    override fun lastIndexOf(element: E): Int {
        return list.lastIndexOf(element)
    }

    override fun listIterator(): MutableListIterator<E> {
        return list.listIterator()
    }

    override fun listIterator(index: Int): MutableListIterator<E> {
        return list.listIterator(index)
    }

    override fun removeAt(index: Int): E {
        synchronized(lock) {
            val item = list.removeAt(index)
            notifyItemRemoved(index)
            return item
        }
    }

    override fun remove(element: E): Boolean {
        synchronized(lock) {
            val index = indexOf(element)
            if (list.remove(element)) {
                notifyItemRemoved(index)
                return true
            } else {
                return false
            }
        }
    }

    override fun removeAll(elements: Collection<E>): Boolean {
        var modified = false
        val iterator = list.iterator()
        while (iterator.hasNext()) {
            val element = iterator.next()
            if (elements.contains(element)) {
                synchronized (lock) {
                    val index = indexOf(element)
                    iterator.remove()
                    notifyItemRemoved(index)
                }
                modified = true
            }
        }
        return modified
    }

    override fun retainAll(elements: Collection<E>): Boolean {
        var modified = false
        val iterator = list.iterator()
        while (iterator.hasNext()) {
            val element = iterator.next()
            if (!elements.contains(element)) {
                synchronized (lock) {
                    val index = indexOf(element)
                    iterator.remove()
                    notifyItemRemoved(index)
                }
                modified = true
            }
        }
        return modified
    }

    override fun set(index: Int, element: E): E {
        synchronized (lock) {
            val origin = list.set(index, element)
            if (!equals(element, origin)) {
                notifyItemChanged(index)
            }
            return origin
        }
    }

    fun equals(a: Any?, b: Any?): Boolean {
        return if (a == null) b == null else a == b
    }

    override fun subList(fromIndex: Int, toIndex: Int): MutableList<E> {
        return list.subList(fromIndex, toIndex)
    }

    fun replaceWith(data: List<E>) {
        if (list.isEmpty() && data.isEmpty()) return


        if (list.isEmpty()) {
            addAll(data)
            return
        }

        if (data.isEmpty()) {
            clear()
            return
        }

        if (list == data) return

        // 首先将旧列表有、新列表没有的从旧列表去除
        retainAll(data)

        // 如果列表被完全清空了，那就直接全部插入好了
        if (list.isEmpty()) {
            addAll(data)
            return
        }

        // 然后遍历新列表，对旧列表的数据更新、移动、增加
        for (indexNew in data.indices) {
            val item = data[indexNew]

            val indexOld = indexOf(item)

            if (indexOld == -1) {
                add(indexNew, item)
            } else if (indexOld == indexNew) {
                set(indexNew, item)
            } else {
                list.removeAt(indexOld)
                list.add(indexNew, item)
                notifyItemMoved(indexOld, indexNew)
            }
        }
    }
}