package com.yeungkc.gank.io.presenter

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.yeungkc.gank.io.R
import com.yeungkc.gank.io.conf.DEFAULT_COUNT
import com.yeungkc.gank.io.model.bean.BaseResult
import com.yeungkc.gank.io.model.bean.Result
import com.yeungkc.gank.io.model.server.GankRequestCallBack
import com.yeungkc.gank.io.model.server.GankServer
import com.yeungkc.gank.io.view.IMainView
import io.realm.Realm
import io.realm.RealmChangeListener
import io.realm.Sort
import org.jetbrains.anko.AnkoAsyncContext
import com.bumptech.glide.request.target.Target as FuckTarget


/**
 * Created by YeungKC on 16/2/24.
 *
 * @项目名: kc
 * @包名: gank.io.kc.presenter
 * @作者: YeungKC
 *
 * @描述：todo
 */
class MainPresenter(override val mView: IMainView) : BasePresenter<IMainView> {

    val mRealm: Realm = Realm.getDefaultInstance()
    val mRealmResults =
            mRealm.where(Result::class.java)
                    .equalTo("type", "福利")
                    .findAllSorted("publishedAt", Sort.DESCENDING)

    val mOnChangeListener = RealmChangeListener { mView.onChange(mRealmResults) }
    val mContext = mView.mContext

    fun getFuli(page: Int, count: Int = DEFAULT_COUNT) {

        GankServer.instance.getFuli(page, count, object :
                GankRequestCallBack<BaseResult<List<Result>>>(mView) {
            override fun onRequestSuccessOnBG(responseBean: BaseResult<List<Result>>,
                                              ankoAsyncContext: AnkoAsyncContext<AnkoAsyncContext<Context>>) {
                decodePicAndSaveDb(responseBean.results)
                super.onRequestSuccessOnBG(responseBean, ankoAsyncContext)
            }

            override fun onRequestSuccess(responseBean: BaseResult<List<Result>>) {
                mView.onRequestComplete()
            }

            override fun onRequestComplete() {
            }

            override fun onRequestFail(responseBean: BaseResult<List<Result>>) =
                    onRequestError(mContext.getString(R.string.network_fail))

            override fun onRequestError(msg: String?) {
                var errorMsg: String

                if (msg == null) {
                    errorMsg = mContext.getString(R.string.network_error)
                } else {
                    errorMsg = msg
                }

                val isHaveCache = mRealmResults.size >= page * DEFAULT_COUNT
                mView.onRequestError(errorMsg, isHaveCache)
                if (isHaveCache) mView.onChange(mRealmResults)

                mView.onRequestComplete()
            }
        })
    }

    private fun decodePicAndSaveDb(results: List<Result>) {
        Realm.getDefaultInstance().apply {
            for (i in results) {
                var bitmap: Bitmap? = null
                try {
                    bitmap = Glide.with(mView.mActivity)
                            .load(i.url)
                            .asBitmap()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(-1, -1)
                            .get()
                } catch(e: Exception) {
                    e.printStackTrace()
                }

                if (bitmap != null) {
                    i.width = bitmap.width
                    i.height = bitmap.height

                    Log.v(javaClass.simpleName, "width : ${i.width} :: height : ${i.height} :: publishedAt : ${i.publishedAt} :: url : ${i.url}")
                }

                beginTransaction()
                copyToRealmOrUpdate(i)
                commitTransaction()
            }
        }
    }

    fun addListener() {
        mRealmResults.addChangeListener (mOnChangeListener)
        mView.onChange(mRealmResults)
    }

    override fun removeListener() {
        mRealmResults.removeChangeListener(mOnChangeListener)
    }
}