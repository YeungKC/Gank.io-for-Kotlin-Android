package com.yeungkc.gank.io.extensions

import android.util.Log
import com.yeungkc.gank.io.model.BaseRequestCallBack
import com.yeungkc.gank.io.model.bean.BaseResult
import org.jetbrains.anko.async
import retrofit2.Call

/**
 * @项目名: kc
 * @包名: gank.io.kc.extensions
 * @作者: YeungKC
 *
 * @描述：  扩展方法: 在 retrofit 的基础上增加 callBack 回调
 */
fun<T> Call<BaseResult<T>>.customExecute(mRequestCallBack: BaseRequestCallBack<BaseResult<T>>): BaseResult<T>? {
    request().apply {
        Log.v(javaClass.simpleName,"Request = url:${url()} :: body:${body()}")
    }

    mRequestCallBack.mV.mContext.async() {
        mRequestCallBack.onPostRequestExecuteOnBG(this)

        try {
            val response = execute()

            if (!response.isSuccess) {
                mRequestCallBack.onRequestCompleteOnBG(null, "fail", this)
                return@async
            }

            mRequestCallBack.onRequestCompleteOnBG(response.body(), null, this)
        } catch(e: Exception) {
            mRequestCallBack.onRequestCompleteOnBG(null, e.toString(), this)
        }
    }
    return null
}
