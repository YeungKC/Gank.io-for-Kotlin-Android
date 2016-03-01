package com.yeungkc.gank.io.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import com.yeungkc.gank.io.R
import com.yeungkc.gank.io.presenter.BasePresenter
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_base.*
import kotlin.properties.Delegates

/**
 * Created by YeungKC on 16/2/24.
 *
 * @项目名: kc
 * @包名: gank.io.kc.ui.activities
 * @作者: YeungKC
 *
 * @描述：TODO
 */
abstract class BaseActivity<P : BasePresenter<*>> : AppCompatActivity() {
    open val mPresenter: P  by Delegates.notNull()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        setSupportActionBar(tl_base_toolbar)
        fl_base_content.addView(provideContentView())

        initView()

        initEvent()

        initData()
    }

    open fun provideContentView(): View =
            LayoutInflater.from(this).inflate(provideContentViewId(), null, false)

    open fun provideContentViewId(): Int = 0

    abstract fun initView()
    abstract fun initEvent()
    abstract fun initData()
    override fun onDestroy() {
        super.onDestroy()
        mPresenter.removeListener()
        Realm.getDefaultInstance().close()
    }
}