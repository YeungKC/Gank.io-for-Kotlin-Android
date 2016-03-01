package com.yeungkc.gank.io.model.server

import com.yeungkc.gank.io.model.bean.BaseResult
import com.yeungkc.gank.io.model.bean.DayResult
import com.yeungkc.gank.io.model.bean.Result
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by YeungKC on 16/2/24.
 *
 * @项目名: kc
 * @包名: gank.io.kc.model.server
 * @作者: YeungKC
 *
 * @描述：TODO
 */
interface IGankServer {

    @GET("data/{categorical}/{count}/{page}")
    fun categoricalData(
            @Path("categorical") categorical: String,
            @Path("page") page: Int,
            @Path("count") count: Int): Call<BaseResult<List<Result>>>


    @GET("day/{year}/{month}/{day}")
     fun getDetailData(
            @Path("year") year: String,
            @Path("month") month: String,
            @Path("day") day: String): Call<BaseResult<DayResult>>
}