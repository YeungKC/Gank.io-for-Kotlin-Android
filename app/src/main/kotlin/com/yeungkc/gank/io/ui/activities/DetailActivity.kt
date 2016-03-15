package com.yeungkc.gank.io.ui.activities

import android.app.Activity
import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import com.yeungkc.gank.io.R
import com.yeungkc.gank.io.model.bean.Result
import com.yeungkc.gank.io.presenter.DetailPresenter
import com.yeungkc.gank.io.ui.adapter.DetailAdapter
import com.yeungkc.gank.io.view.IDetailVIew
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates
import org.jetbrains.anko.startActivity as start

/**
 * Created by YeungKC on 16/2/29.
 *
 * @项目名: kc
 * @包名: gank.io.kc.ui.activities
 * @作者: YeungKC
 *
 * @描述：TODO
 */
class DetailActivity : BaseActivity<DetailPresenter>(), ToolbarManager, IDetailVIew {


    override val mActivity: Activity = this
    override val mContext: Context = this
    override val mPresenter: DetailPresenter by lazy { DetailPresenter(this) }
    var publishedAt: Date by Delegates.notNull()
    override val toolbar: Toolbar by lazy { tl_base_toolbar }
    override var lastTime: Long = 0
    val LayoutManager = LinearLayoutManager(mContext)
    val mAdapter = DetailAdapter(mContext)

    companion object {
        val DATE = "DATE"
    }

    override fun provideContentViewId(): Int = R.layout.activity_detail

    override fun initView() {
        srl_detail_refresh.setColorSchemeResources(R.color.colorPrimary)

        rv_detail_content.layoutManager = LayoutManager
        rv_detail_content.adapter = mAdapter
    }

    override fun initEvent() {
        enableHomeAsUp { finish() }
        srl_detail_refresh.setOnRefreshListener { mPresenter.getDetail(publishedAt) }
        mAdapter.setOnItemClickListener { result ->
            start<WebViewActivity>(
                    WebViewActivity.URL to result.url!!,
                    WebViewActivity.TITLE to result.desc!!)
        }

        enableDoubleClickToEvent {
            rv_detail_content.scrollToPosition(0)
        }
    }

    override fun initData() {
        publishedAt = intent.getSerializableExtra(DATE) as Date
        title = SimpleDateFormat("yyyy-MM-dd").format(publishedAt)
        mPresenter.addListener(publishedAt)
        mPresenter.getDetail(publishedAt)
    }

    override fun onChange(results: List<Result>) {
        mAdapter.replaceWith(results)
    }

    override fun onPostRequestExecute() {
        srl_detail_refresh.isRefreshing = true
    }

    override fun onRequestComplete() {
        srl_detail_refresh.isRefreshing = false
    }

    override fun onRequestError(str: String, isHaveCache: Boolean) {
        var SbStr = str
        if (isHaveCache) {
            SbStr = str + getString(R.string.have_cache)
        }
//        val make = Snackbar.make(rv_detail_content, SbStr, Snackbar.LENGTH_LONG)
//
//        if (!isHaveCache)
//            make.setAction(R.string.retry) { v ->
//                getDetail()
//            }
//
//        make.show()
        toast(SbStr)
    }

    fun getDetail() {
        if (!srl_detail_refresh.isRefreshing) mPresenter.getDetail(publishedAt)
    }
}
