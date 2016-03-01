package com.yeungkc.gank.io.model.bean

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

/**
 * Created by YeungKC on 16/2/24.
 *
 * @项目名: kc
 * @包名: gank.io.kc.model.bean
 * @作者: YeungKC
 *
 * @描述：TODO
 */
open class BaseResult<T>(
        var error: Boolean,
        var results: T) {
}

open class Result(
        @PrimaryKey
        @SerializedName("_id")
        open var id: String? = null,
        open var url: String? = null,
        open var desc: String? = null,
        open var publishedAt: Date? = null,
        open var who: String? = null,
        open var type: String? = null,
        open var width: Int = 0,
        open var height: Int = 0
) : RealmObject()

open class DayResult(
        @SerializedName("福利")
        open var fuLiList: List<Result> = listOf<Result>(),
        @SerializedName("Android")
        open var androidList: List<Result> = listOf<Result>(),
        @SerializedName("iOS")
        open var iosList: List<Result> = listOf<Result>(),
        @SerializedName("休息视频")
        open var videoList: List<Result> = listOf<Result>(),
        @SerializedName("拓展资源")
        open var expandResourcesList: List<Result> = listOf<Result>(),
        @SerializedName("前端")
        open var frontEndList: List<Result> = listOf<Result>(),
        @SerializedName("瞎推荐")
        open var recommendList: List<Result> = listOf<Result>(),
        @SerializedName("App")
        open var appList: List<Result> = listOf<Result>()
)