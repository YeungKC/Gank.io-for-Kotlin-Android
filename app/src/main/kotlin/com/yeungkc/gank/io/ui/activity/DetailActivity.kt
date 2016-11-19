package com.yeungkc.gank.io.ui.activity

import android.os.Bundle
import android.support.v4.view.WindowInsetsCompat
import android.support.v7.widget.Toolbar
import com.yeungkc.gank.io.databinding.ActivityToolbarBaseBinding
import com.yeungkc.gank.io.ui.fragment.DetailFragment
import java.text.SimpleDateFormat
import java.util.*

class DetailActivity : BaseToolBarActivity() {
    private val binding by lazy { ActivityToolbarBaseBinding.inflate(layoutInflater) }
    override fun getToolBar(): Toolbar = binding.tlBaseToolbar

    override fun onCreateView() {
        setContentView(binding.root)
    }

    lateinit var date: Date

    companion object {
        const val DATE = "DATE"
        const val TAG = "DetailActivity"
    }

    override fun initArgs(savedInstanceState: Bundle?) {
        super.initArgs(savedInstanceState)
        date = intent.getSerializableExtra(DATE) as Date
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        onToolBarCreate(savedInstanceState)
        this.title = SimpleDateFormat("yyyy-MM-dd").format(date)
    }

    override fun onApplyWindowInsets(savedInstanceState: Bundle?, insets: WindowInsetsCompat) {
        super.onApplyWindowInsets(savedInstanceState, insets)

        val transaction = supportFragmentManager.beginTransaction()

        val findFragmentByTag = supportFragmentManager.findFragmentByTag(TAG)
        if (findFragmentByTag != null) {
            transaction.show(findFragmentByTag)
        } else {
            transaction.add(
                    binding.flBaseContent.id,
                    DetailFragment.newInstance(date,insets.systemWindowInsetTop, insets.systemWindowInsetBottom),
                    TAG
            )
        }

        transaction.commit()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        onSaveToolbarInstanceState(outState)
    }
}