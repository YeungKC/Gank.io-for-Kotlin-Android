package com.yeungkc.gank.io.ui.activities

import android.app.Activity
import android.content.Context
import android.support.v7.widget.Toolbar
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.yeungkc.gank.io.R
import com.yeungkc.gank.io.presenter.WebViewPresenter
import com.yeungkc.gank.io.view.IWebViewView
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.activity_web_view.*

/**
 * Created by YeungKC on 16/3/1.
 *
 * @项目名: kc
 * @包名: gank.io.kc.ui.activities
 * @作者: YeungKC
 *
 * @描述：TODO
 */
class WebViewActivity : BaseActivity<WebViewPresenter>(), ToolbarManager, IWebViewView {
    lateinit var mUrl: String
    lateinit var mTitle: String

    companion object {
        val URL = "URL"
        val TITLE = "TITLE"
    }

    override val mPresenter: WebViewPresenter by lazy { WebViewPresenter(this) }

    override val mActivity: Activity = this
    override val mContext: Context = this

    override val toolbar: Toolbar by lazy { tl_base_toolbar }
    override var lastTime: Long = 0

    override fun provideContentViewId(): Int = R.layout.activity_web_view


    override fun initView() {
        srl_web_refresh.setColorSchemeResources(R.color.colorPrimary)

        val settings = wv_web.settings
        settings.javaScriptEnabled = true
        settings.loadWithOverviewMode = true
        settings.setAppCacheEnabled(true)
        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        settings.setSupportZoom(true)
    }

    override fun initEvent() {
        enableHomeAsUp { finish() }

        wv_web.setWebViewClient(object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String?): Boolean {
                if (url != null) view.loadUrl(url)
                return true
            }
        })

        wv_web.setWebChromeClient(object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                if (newProgress == 100) onRequestComplete() else onPostRequestExecute()
            }

            override fun onReceivedTitle(view: WebView?, title: String?) {
                super.onReceivedTitle(view, title)
                this@WebViewActivity.title = title
            }
        })
    }

    override fun initData() {
        mUrl = intent.getStringExtra(URL)
        mTitle = intent.getStringExtra(TITLE)

        title = mTitle

        wv_web.loadUrl(mUrl)
    }

    override fun onPostRequestExecute() {
        if (!srl_web_refresh.isRefreshing) srl_web_refresh.isRefreshing = true
    }

    override fun onRequestComplete() {
        if (srl_web_refresh.isRefreshing) srl_web_refresh.isRefreshing = false
    }

    override fun onDestroy() {
        wv_web.destroy()
        super.onDestroy()
    }

    override fun onPause() {
        wv_web.onPause()
        super.onPause()
    }

    override fun onResume() {
        wv_web.onResume()
        super.onResume()
    }

    override fun onBackPressed() {
        if (wv_web.canGoBack()) wv_web.goBack() else super.onBackPressed()
    }
}
