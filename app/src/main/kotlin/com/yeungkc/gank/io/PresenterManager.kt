package com.yeungkc.gank.io

import java.util.*


object PresenterManager {
    private @JvmStatic val map = HashMap<String, Any>()

    fun put(key: String, presenter: Any) = map.put(key, presenter)

    @Suppress("UNCHECKED_CAST")
    fun <T> getPresenter(key: String, getter: () -> T): T {
        if (map.contains(key)) return map[key] as T

        val t = getter()

        if (t != null) put(key, t)
        return t
    }

    fun removePresenter(key: String) = map.remove(key)
}