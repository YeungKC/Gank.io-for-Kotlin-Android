package com.yeungkc.gank.io.presenter

import com.yeungkc.gank.io.view.IWebViewView

/**
 * Created by YeungKC on 16/3/1.
 *
 * @项目名: kc
 * @包名: gank.io.kc.presenter
 * @作者: YeungKC
 *
 * @描述：TODO
 */
class WebViewPresenter(override val mView: IWebViewView) : BasePresenter<IWebViewView> {

    override fun removeListener() {
    }
}
