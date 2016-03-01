package com.yeungkc.gank.io.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.yeungkc.gank.io.R
import com.yeungkc.gank.io.ui.activities.ToolbarManager
import kotlinx.android.synthetic.main.activity_about.*

/**
 * Created by YeungKC on 16/3/1.
 *
 * @项目名: kc
 * @包名: gank.io.kc.ui.activities
 * @作者: YeungKC
 *
 * @描述：TODO
 */
class AboutActivity : AppCompatActivity(), ToolbarManager {
    override val toolbar: Toolbar by lazy { tl_about_toolbar }
    override var lastTime: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        enableHomeAsUp { finish() }
    }
}
