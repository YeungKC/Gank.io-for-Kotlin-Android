package com.yeungkc.gank.io.model

import android.content.Context
import com.yeungkc.gank.io.model.bean.BaseResult
import com.yeungkc.gank.io.view.IView
import org.jetbrains.anko.AnkoAsyncContext
import org.jetbrains.anko.async
import org.jetbrains.anko.uiThread

/**
 * Created by YeungKC on 15/12/26.

 * @项目名: Gold wallet
 * *
 * @包名: com.goldwallet.parser
 * *
 * @作者: YeungKC
 * *
 * @描述：TODO
 */
abstract class BaseRequestCallBack<B : BaseResult<*>>(val mV: IView) {

    open fun onPostRequestExecuteOnBG(ankoAsyncContext: AnkoAsyncContext<Context>) {
        ankoAsyncContext.uiThread {
            onPostRequestExecute()
        }
    }

    open fun onPostRequestExecute() {
        mV.onPostRequestExecute()
    }

    open fun onRequestCompleteOnBG(responseBean: B?, msg: String?, ankoAsyncContext: AnkoAsyncContext<Context>) {
        ankoAsyncContext.uiThread {
            onRequestComplete()

            ankoAsyncContext.async() {
                if (responseBean == null) {
                    onRequestErrorOnBG(null, this)
                    return@async
                }

                if (isSuccess(responseBean)) onRequestFailOnBG(responseBean, this) else onRequestSuccessOnBG(responseBean, this)
            }
        }
    }

    abstract fun isSuccess(responseBean: B): Boolean

    open fun onRequestComplete() {
        mV.onRequestComplete()
    }

    open fun onRequestErrorOnBG(msg: String?, ankoAsyncContext: AnkoAsyncContext<AnkoAsyncContext<Context>>) {
        ankoAsyncContext.uiThread { onRequestError(msg) }
    }

    open fun onRequestSuccessOnBG(responseBean: B, ankoAsyncContext: AnkoAsyncContext<AnkoAsyncContext<Context>>) {
        ankoAsyncContext.uiThread { onRequestSuccess(responseBean) }
    }


    open fun onRequestFailOnBG(responseBean: B, ankoAsyncContext: AnkoAsyncContext<AnkoAsyncContext<Context>>) {
        ankoAsyncContext.uiThread { onRequestFail(responseBean) }
    }

    abstract fun onRequestError(msg: String?)

    open fun onRequestSuccess(responseBean: B) {
    }

    abstract fun onRequestFail(responseBean: B)
}
