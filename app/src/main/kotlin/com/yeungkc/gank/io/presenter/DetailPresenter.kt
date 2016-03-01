package com.yeungkc.gank.io.presenter

import android.content.Context
import com.yeungkc.gank.io.R
import com.yeungkc.gank.io.model.bean.BaseResult
import com.yeungkc.gank.io.model.bean.DayResult
import com.yeungkc.gank.io.model.bean.Result
import com.yeungkc.gank.io.model.server.GankRequestCallBack
import com.yeungkc.gank.io.model.server.GankServer
import com.yeungkc.gank.io.view.IDetailVIew
import io.realm.Realm
import io.realm.RealmChangeListener
import org.jetbrains.anko.AnkoAsyncContext
import java.util.*
import kotlin.properties.Delegates

/**
 * Created by YeungKC on 16/2/29.
 *
 * @项目名: kc
 * @包名: gank.io.kc.presenter
 * @作者: YeungKC
 *
 * @描述：TODO
 */
class DetailPresenter(override val mView: IDetailVIew) : BasePresenter<IDetailVIew> {
    var publishedAt: Date by Delegates.notNull()

    val mRealm: Realm = Realm.getDefaultInstance()
    val mRealmResults by lazy {
        mRealm.where(Result::class.java)
                .equalTo("publishedAt", publishedAt)
                .notEqualTo("type", "福利")
                .findAllSorted("type")
    }
    val mOnChangeListener = RealmChangeListener {
        mView.onChange(mRealmResults)
    }


    fun addListener(publishedAt: Date) {
        this.publishedAt = publishedAt
        mRealmResults.addChangeListener (mOnChangeListener)
        mView.onChange(mRealmResults)
    }

    override fun removeListener() {
        mRealmResults.removeChangeListener(mOnChangeListener)
    }

    fun close() {
        Realm.getDefaultInstance().close()
    }

    fun getDetail(publishedAt: Date) {
        GankServer.instance.getDetail(publishedAt, object : GankRequestCallBack<BaseResult<DayResult>>(mView) {
            override fun onRequestFail(responseBean: BaseResult<DayResult>) =
                    onRequestError(mView.mContext.getString(R.string.network_fail))

            override fun onRequestError(msg: String?) {
                var errorMsg: String

                if (msg == null) {
                    errorMsg = mView.mContext.getString(R.string.network_error)
                } else {
                    errorMsg = msg
                }

                val isHaveCache = mRealmResults.size > 0
                mView.onRequestError(errorMsg, isHaveCache)
                if (isHaveCache) mView.onChange(mRealmResults)
            }

            override fun onRequestSuccessOnBG(responseBean: BaseResult<DayResult>, ankoAsyncContext: AnkoAsyncContext<AnkoAsyncContext<Context>>) {
                super.onRequestSuccessOnBG(responseBean, ankoAsyncContext)
                saveDb(responseBean.results)
            }
        })
    }

    fun saveDb(dayResult: DayResult) {
        Realm.getDefaultInstance().apply {
            beginTransaction()

            val arrayList = ArrayList<Result>()
            with(dayResult) {
                arrayList.addAll(androidList)
                arrayList.addAll(iosList)
                arrayList.addAll(videoList)
                arrayList.addAll(expandResourcesList)
                arrayList.addAll(frontEndList)
                arrayList.addAll(recommendList)
                arrayList.addAll(appList)
            }

            for (i in arrayList) {
                i.publishedAt = publishedAt
                copyToRealmOrUpdate(i)
            }

            commitTransaction()
        }
    }
}