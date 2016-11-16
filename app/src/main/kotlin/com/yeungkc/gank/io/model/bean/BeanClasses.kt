package com.yeungkc.gank.io.model.bean

import android.content.Context
import com.google.gson.annotations.SerializedName
import com.yeungkc.gank.io.R
import com.yeungkc.gank.io.extensions.getResourcesString
import java.io.Serializable
import java.util.*

interface AutoBean {
    val itemType: Int
    val itemId: Long
}

open class BaseResult<T>(
        var error: Boolean,
        var results: T)

open class Subtitle(val subTitle: String) : AutoBean, Serializable {
    companion object {
        const val GANK_SUBTITLE_TYPE = 0
    }

    override val itemType: Int
        get() = GANK_SUBTITLE_TYPE

    override val itemId: Long
        get() = subTitle.hashCode().toLong()

    fun getIcon(context: Context): Int {
        return when (subTitle) {
            context.getResourcesString(R.string.android) -> R.drawable.ic_android
            context.getResourcesString(R.string.ios) -> R.drawable.ic_apple
            context.getResourcesString(R.string.前端) -> R.drawable.ic_html5
            context.getResourcesString(R.string.app) -> R.drawable.ic_apps
            context.getResourcesString(R.string.瞎推荐) -> R.drawable.ic_grade
            context.getResourcesString(R.string.拓展资源) -> R.drawable.ic_explore
            context.getResourcesString(R.string.movie) -> R.drawable.ic_movie
            else -> R.drawable.ic_home
        }
    }
}

open class Result() : AutoBean, Serializable {
    companion object {
        val GANK_PIC_TYPE = "福利".hashCode()
        const val GANK_TYPE = 1
    }

    override val itemId: Long
        get() = hashCode().toLong()
    override val itemType: Int
        get() = type.hashCode()

    lateinit var _id: String
    var url: String? = null
    var desc: String? = null
    lateinit var publishedAt: Date
    var who: String? = null
    lateinit var type: String
    var width: Int = 0
    var height: Int = 0
    var shotLoadingPlaceholderColor: Int = 0

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as Result

        if (_id != other._id) return false

        return true
    }

    override fun hashCode(): Int {
        return _id.hashCode()
    }

    override fun toString(): String {
        return "Result(desc=$desc)"
    }
}

open class DayResult(
        @SerializedName("福利")
        open var 福利List: List<Result> = listOf<Result>(),
        @SerializedName("Android")
        open var androidList: List<Result> = listOf<Result>(),
        @SerializedName("iOS")
        open var iosList: List<Result> = listOf<Result>(),
        @SerializedName("休息视频")
        open var 休息视频List: List<Result> = listOf<Result>(),
        @SerializedName("拓展资源")
        open var 拓展资源List: List<Result> = listOf<Result>(),
        @SerializedName("前端")
        open var 前端List: List<Result> = listOf<Result>(),
        @SerializedName("瞎推荐")
        open var 瞎推荐List: List<Result> = listOf<Result>(),
        @SerializedName("App")
        open var appList: List<Result> = listOf<Result>()
)