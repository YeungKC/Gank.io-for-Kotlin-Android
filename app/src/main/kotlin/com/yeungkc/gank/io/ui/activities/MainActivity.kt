package com.yeungkc.gank.io.ui.activities

import android.app.Activity
import android.content.Context
import android.support.design.widget.Snackbar
import android.support.v7.widget.StaggeredGridLayoutManager
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.yeungkc.gank.io.R
import com.yeungkc.gank.io.conf.DEFAULT_COUNT
import com.yeungkc.gank.io.extensions.moreLoading
import com.yeungkc.gank.io.extensions.setOnItemClickListener
import com.yeungkc.gank.io.model.bean.Result
import com.yeungkc.gank.io.presenter.MainPresenter
import com.yeungkc.gank.io.ui.adapter.GankAdapter
import com.yeungkc.gank.io.view.IMainView
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity as start

class MainActivity : BaseActivity<MainPresenter>(), ToolbarManager, IMainView {
    override val mActivity: Activity = this
    override val mContext: Context = this

    val LayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
    val mAdapter = GankAdapter()
    override val mPresenter: MainPresenter = MainPresenter(this)
    // set page 自动更新 totalCount
    var page = 1
        set(value) {
            totalCount = value * DEFAULT_COUNT
            field = value
        }
    var totalCount = DEFAULT_COUNT
    override val toolbar: Toolbar by lazy { tl_base_toolbar }
    override var lastTime: Long = 0
    lateinit var mResults: List<Result>


    override fun provideContentViewId(): Int = R.layout.activity_main

    override fun initView() {
        srl_main_refresh.setColorSchemeResources(R.color.colorPrimary)

        rv_main_content.layoutManager = LayoutManager
        rv_main_content.adapter = mAdapter
    }

    override fun initEvent() {
        // 双击 toolbar 返回顶部, 判断当前位置, 超过 50 立即返回忽略动   画效果
        enableDoubleClickToEvent {
            val LastPositions = LayoutManager.findLastVisibleItemPositions(null)

            if (Math.max(LastPositions[0], LastPositions[1]) > 50)
                LayoutManager.scrollToPositionWithOffset(0, 0)
            else
                rv_main_content.smoothScrollToPosition(0)
        }

        srl_main_refresh.setOnRefreshListener {
            page = 1
            mPresenter.getFuli(page)
        }

        rv_main_content.setOnItemClickListener { recyclerView, position, view ->
            with(mResults[position]) {
                //                startActivity(
                //                        Intent(mContext, DetailActivity::class.java)
                //                                .putExtra(DetailActivity.DATE, publishedAt))
                //
                start<DetailActivity>(DetailActivity.DATE to  publishedAt!!)
            }
        }

        // 加载更多
        rv_main_content.moreLoading { position ->
            Log.v(javaClass.simpleName, "position:$position :: itemCount:${mAdapter.itemCount} :: totalCount:$totalCount")
            if (position + 1 == totalCount)
                getFuli(++page)
        }
    }

    override fun initData() {
        mPresenter.addListener()
        getFuli(page)
    }

    fun getFuli(page: Int) {
        if (srl_main_refresh.isRefreshing) return
        mPresenter.getFuli(page)
    }

    override fun onPostRequestExecute() {
        srl_main_refresh.post { srl_main_refresh.isRefreshing = true }
    }

    override fun onRequestComplete() {
        srl_main_refresh.isRefreshing = false
    }

    override fun onRequestSuccess() {
        Snackbar.make(rv_main_content, R.string.on_request_success,Snackbar.LENGTH_LONG).show()
    }

    //  判断是否有缓存决定 view 显示方式
    override fun onRequestError(str: String, isHaveCache: Boolean) {
        var SbStr = str
        if (isHaveCache) {
            SbStr = str + getString(R.string.have_cache)
        } else {
            --page
        }
        val make = Snackbar.make(rv_main_content, SbStr, Snackbar.LENGTH_LONG)

        if (!isHaveCache)
            make.setAction(R.string.retry) { v ->
                getFuli(++page)
            }

        make.show()
    }

    // 最终 rv itemCount 是由 totalCount 决定, 即 page * DEFAULT_COUNT
    override fun onChange(results: List<Result>) {
        mResults = results
        mAdapter.replaceWith(results.let {
            if (it.size < totalCount) it else it.subList(0, totalCount)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            start<AboutActivity>()
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}
